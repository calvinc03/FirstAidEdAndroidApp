package com.calvinchen.firstaidlearning

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ImageSpan
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.text.bold
import androidx.core.text.inSpans
import androidx.core.text.scale
import kotlinx.android.synthetic.main.fragment_infopage.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class InfoPageFragment : Fragment() {

    private lateinit var pageViews : JSONArray
    private var s = SpannableStringBuilder()
    private var screenWidth : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infopage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let { InfoPageFragmentArgs.fromBundle(it) }
        val fileName : String? = args?.file?.replace(" ", "_")?.toLowerCase() + ".json"

        loadJson(this.requireContext(), fileName)
        buildPage(view)
    }

    private fun loadJson(context: Context, file : String?) {
        val jsonString : String?
        var inputStream : InputStream? = null

        try {
            inputStream = file?.let { context.assets.open(it) }
            jsonString = inputStream?.bufferedReader().use{it?.readText()}

            pageViews = JSONArray(jsonString)
        } catch (e : IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
    }

    private fun buildPage(view : View) {
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        screenWidth = metrics.widthPixels - 20

        for (i in 0 until pageViews.length()) {
            val obj : JSONObject = pageViews[i] as JSONObject
            val key = obj.keys().next()

            createElement(key, obj.get(key))
        }

        test_text.text = s
    }

    private fun createElement(key : String, value : Any) {
        when (key) {
            "header" -> {
                s.scale(1.2F) { bold {append("\n" + value as String + "\n\n")}}
            }
            "image" -> {
                s.append("\n")
                val drawable = resources.getDrawable(resources.getIdentifier(value as String, "drawable", activity?.packageName), null)
                drawable.setBounds(0, 0, screenWidth, (drawable.intrinsicHeight * screenWidth) / drawable.intrinsicWidth)
                s.inSpans(ImageSpan(drawable)) {append(" ")}
            }
            "paragraph" -> {
                s.append("\n" + value as String + "\n")
            }
            "points" -> {
                s.append("\n")

                val list = value as JSONArray
                for (i in 0 until list.length()) {
                    if (Build.VERSION.SDK_INT >= 28)
                        s.inSpans(BulletSpan(15, Color.BLACK, 10)) { scale(0.9F) {append(list[i] as String + "\n")}}
                    else
                        s.inSpans(BulletSpan(15)) { scale(0.9F) {append(list[i] as String + "\n")}}
                }
            }
            "table" -> {
                return
            }
            else -> return
        }
    }
}