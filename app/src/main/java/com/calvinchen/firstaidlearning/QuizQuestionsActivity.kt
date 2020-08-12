package com.calvinchen.firstaidlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation

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

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.quiz_nav_fragment)
        if (navController.currentDestination?.id == R.id.quizQuestionsFragment) {
             AlertDialog.Builder(this)
                 .setTitle("Go Back")
                 .setMessage("Are you sure you want to leave this quiz? All progress will be lost.")

                 // Specifying a listener allows you to take an action before dismissing the dialog.
                 // The dialog is automatically dismissed when a dialog button is clicked.
                 .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }

                 .setNegativeButton("No", null)
                 .show()
        }
        else super.onBackPressed()
    }

    fun navigateToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}