package com.calvinchen.firstaidlearning

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.ImageSpan
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.inSpans
import androidx.core.text.scale
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_infopage.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*


class InfoPageActivity : AppCompatActivity() {

    private lateinit var pageViews : JSONArray
    private lateinit var fileName : String
    private var screenWidth : Int = 0

    private var s = SpannableStringBuilder()
    private var fontSize : Float = 18F
    private var startTime = 0L

    private var highlighted : String = ""
    private var highlightSpanList = mutableListOf<BackgroundColorSpan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infopage)

        fileName = intent.extras?.get("file") as String
        val chapterNum = intent.extras?.get("chapter") as String

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = fileName

        val chapterJSON = Constants.getJSONObjectFromFile(this, "$chapterNum.json")
        pageViews = chapterJSON.get(fileName) as JSONArray

        buildPage()

        startTime = System.currentTimeMillis()

        setupActionModeCallBack()
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(supportActionBar?.title as String, highlighted)
            putFloat("textSize", fontSize)
            commit()
        }

        val sharedStats = getSharedPreferences("statistics", Context.MODE_PRIVATE)
        with (sharedStats.edit()) {
            putLong("ReadingTime", (sharedStats.getLong("ReadingTime", 0L) + System.currentTimeMillis() -
                    startTime))
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_infopage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.text_small -> {
                fontSize--
                fontSize = if (fontSize < 10F) 10F else fontSize
                info_text.textSize = fontSize
            }
            R.id.text_large -> {
                fontSize++
                fontSize = if (fontSize > 24F) 24F else fontSize
                info_text.textSize = fontSize
            }
            R.id.remove_highlight -> {
                for (span in highlightSpanList) {
                    s.removeSpan(span)
                }
                highlighted = ""
                highlightSpanList.clear()
                info_text.text = s
            }
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun buildPage() {
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metrics)
        screenWidth = metrics.widthPixels - 70

        for (i in 0 until pageViews.length()) {
            val obj : JSONObject = pageViews[i] as JSONObject
            val key = obj.keys().next()

            createElement(key, obj.get(key))
        }

        applyPreviousHighlights()
        info_text.text = s

        fontSize = getPreferences(Context.MODE_PRIVATE).getFloat("textSize", 18F)
        info_text.textSize = fontSize
    }

    private fun createElement(key : String, value : Any) {
        when (key) {
            "header" -> {
                s.scale(1.2F) { bold {append("\n" + value as String + "\n")}}
            }
            "image" -> {
                s.append("\n")
                val drawable = resources.getDrawable(resources.getIdentifier(value as String, "drawable", packageName), null)
                drawable.setBounds(0, 0, screenWidth, (drawable.intrinsicHeight * screenWidth) / drawable.intrinsicWidth)
                s.inSpans(ImageSpan(drawable)) {append(" ")}
                s.append("\n")
            }
            "paragraph" -> {
                s.append("\n" + value as String + "\n")
            }
            "points" -> {
                s.append("\n")

                val list = value as JSONArray
                for (i in 0 until list.length()) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        s.inSpans(BulletSpan(15, Color.BLACK, 8)) { scale(0.95F) { append(list[i] as String + "\n") } }
                        s.scale(0.2F) {append("\n")}
                    }
                    else {
                        s.inSpans(BulletSpan(15)) { scale(0.95F) { append(list[i] as String  + "\n") } }
                        s.scale(0.2F) {append("\n")}
                    }
                }
            }
            else -> return
        }
    }

    private fun applyPreviousHighlights() {
        highlighted = getPreferences(Context.MODE_PRIVATE).getString(fileName, "")?: return
        if (highlighted === "") return

        val highlights = highlighted.split(" ").toTypedArray()
        for (i in 0 until highlights.size - 1 step 2) {
            val span = BackgroundColorSpan(Color.YELLOW)
            highlightSpanList.add(span)

            s.setSpan(span, highlights[i].toInt(), highlights[i+1].toInt(), 0)
        }
    }

    private fun setupActionModeCallBack() {
        info_text.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                if (item.itemId == R.id.highlight_text) {
                    val start = info_text.selectionStart
                    val end = info_text.selectionEnd

                    val span = BackgroundColorSpan(Color.YELLOW)
                    highlightSpanList.add(span)

                    s.setSpan(span, start, end, 0)
                    info_text.text = s

                    highlighted += "$start $end "
                }
                return false
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                mode.menuInflater.inflate(R.menu.menu_highlight, menu)
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {}

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }
        }
    }
}