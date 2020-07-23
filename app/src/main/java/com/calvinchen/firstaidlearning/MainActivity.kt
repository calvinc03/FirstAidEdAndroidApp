package com.calvinchen.firstaidlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    public var studyStack = Stack<String>()
    public var practiceStack = Stack<String>()
    public var progressStack = Stack<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)
        setupActionBar(navController)
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
    }

    private fun setupBottomNavMenu(navController: NavController) {
        NavigationUI.setupWithNavController(bottom_nav, navController)
//        bottom_nav.setOnNavigationItemSelectedListener {
//            navigateToSelectedItem(it)
//            true
//        }
    }

    private fun navigateToSelectedItem(item: MenuItem) {
        when (item.itemId) {
            R.id.destination_study -> {
                val openChapter = StudyFragmentDirections.openChapter("xd")
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(openChapter)
            }
        }
    }

    private fun setupActionBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.study_fragment,
                R.id.destination_practice,
                R.id.destination_home
        ).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onBackPressed() {
        println("onBackPressed")
        super.onBackPressed()
    }

    fun navigateToInfoPage(chapterName : String, pageName : String) {
        progressBar.visibility = View.VISIBLE
        val i = Intent(this, InfoPageActivity::class.java)
        i.putExtra("file", pageName)
        i.putExtra("chapter", chapterName)
        startActivity(i)
    }

    fun navigateToQuizQuestions(quizName : String) {
        progressBar.visibility = View.VISIBLE
        val i = Intent(this, QuizQuestionsActivity::class.java)
        i.putExtra("quiz", quizName)
        startActivity(i)
    }
}