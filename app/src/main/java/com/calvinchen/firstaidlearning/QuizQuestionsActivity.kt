package com.calvinchen.firstaidlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONArray

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var chapterQuiz : String

    lateinit var questionList : JSONArray
    lateinit var questionNumbers: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        chapterQuiz = intent.extras?.get("quiz") as String
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = chapterQuiz.replace("_", " ").substring(0, 14)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}