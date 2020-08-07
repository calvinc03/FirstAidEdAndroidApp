package com.calvinchen.firstaidlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var chapterQuiz : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        chapterQuiz = intent.extras?.get("quiz") as String
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = chapterQuiz.replace("_", " ")

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun navigateToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}