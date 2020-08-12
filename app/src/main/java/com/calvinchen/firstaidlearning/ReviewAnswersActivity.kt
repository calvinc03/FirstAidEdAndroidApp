package com.calvinchen.firstaidlearning

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_review_answers.*
import java.io.BufferedReader
import java.io.InputStreamReader

class ReviewAnswersActivity : AppCompatActivity() {

    private lateinit var chapter : String
    private lateinit var answerList : List<String>

    private var numQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_answers)

        chapter = intent.extras?.get("chapter") as String
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "${chapter.replace("_", " ")} results"

        numQuestions = Constants.getNumQuestions(chapter)

        begin_quiz_btn.setOnClickListener { navigateToQuizQuestions(chapter) }
        configureDisplay()
    }

    override fun onResume() {
        super.onResume()
        configureDisplay()
    }

    @SuppressLint("SetTextI18n")
    private fun configureDisplay() {
        getFromInternalStorage()
        if (answerList.size == 1) return
        else {
            val score = getSharedPreferences("quizResults", Context.MODE_PRIVATE).getInt(chapter, -1)
            best_score.text = "Your Best Score: $score/$numQuestions"

            displayResults()
        }
    }

    private fun getFromInternalStorage() {
        var results = ""
        try {
            val inputStream = openFileInput(chapter)
            val br = BufferedReader(InputStreamReader(inputStream))

            var line : String? = null
            while ({line=br.readLine(); line}() != null) {
                line?.let { results += it }
            }

        } catch (e : Exception) {
            e.printStackTrace()
        }

        answerList = results.split("|||")
    }

    private fun displayResults() {
        val inf : LayoutInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (s in answerList) {
            val values = s.split(":::")

            val v : View = inf.inflate(R.layout.layout_question_answer, review_activity, false)

            val image : ImageView = v.findViewById(R.id.answer_correct_image)
            if (values[2].toBoolean()) {
                image.setImageResource(R.drawable.ic_right)
                v.setBackgroundColor(Color.argb(255,179, 232, 179))
            } else {
                image.setImageResource(R.drawable.ic_wrong)
                v.setBackgroundColor(Color.argb(255,214, 201, 201))
            }

            val tvQuestion : TextView = v.findViewById(R.id.review_question)
            tvQuestion.text = values[0]

            val tvAnswer : TextView = v.findViewById(R.id.review_answer)
            tvAnswer.text = values[1]

            v.setOnClickListener {
                val layout : LinearLayout = v.findViewById(R.id.review_answer_display)
                if (layout.visibility == View.GONE) layout.visibility = View.VISIBLE
                else layout.visibility = View.GONE
            }

            review_activity.addView(v)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun navigateToQuizQuestions(quizName : String) {
        val i = Intent(this, QuizQuestionsActivity::class.java)
        i.putExtra("quiz", quizName)
        startActivity(i)
    }
}