package com.calvinchen.firstaidlearning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var studyNav = ""
    var lookupNav = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)
        setupActionBar(navController)

        val sharedCon = getSharedPreferences("Terms and Conditions", Context.MODE_PRIVATE)
        if (!sharedCon.getBoolean("accepted", false)) {
            AlertDialog.Builder(this)
                .setTitle("Disclaimer")
                .setMessage("I do not own any of the content in this app. All pages and " +
                        "information is taken from https://www.sja" +
                        ".ca/English/Courses-and-Training/Pages/firstaidbook.aspx. If you enjoy " +
                        "this material, please consider taking a Standard First Aid Course. All " +
                        "icons and images are taken from icons8.com and flaticon.com." +
                        "\n\nThis app is meant for people who are interested in learning a bit " +
                        "about first aid and can never replace the knowledge gained from a " +
                        "practical first aid course. The information from this app should not " +
                        "empower any individual to perform any first aid they are not certified " +
                        "for. \n\nIf you have any comments or suggestions, please leave a comment" +
                        " and I will do my best to improve the app.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("I UNDERSTAND") { _, _ ->
                    sharedCon.edit().putBoolean("accepted", true).apply()
                }
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
    }

    private fun setupBottomNavMenu(navController: NavController) {
        NavigationUI.setupWithNavController(bottom_nav, navController)
        bottom_nav.setOnNavigationItemSelectedListener {
            navigateToSelectedItem(it, navController)
        }
    }

    private fun navigateToSelectedItem(item: MenuItem, navController: NavController) : Boolean {
        NavigationUI.onNavDestinationSelected(item, navController)

        when (item.itemId) {
            R.id.destination_study -> {
                if (studyNav != "") {
                    val openChapter = StudyFragmentDirections.openChapter(studyNav)
                    navController.navigate(openChapter)
                }
            }
            R.id.destination_lookup -> {
                if (lookupNav != -1) {
                    val openDetails = LookupListFragmentDirections.openDetails(lookupNav)
                    navController.navigate(openDetails)
                }
            }
        }

        return true
    }

    private fun setupActionBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.study_fragment,
                R.id.destination_practice,
                R.id.destination_home,
                R.id.lookupListFragment
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
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        when (navController.currentDestination?.id) {
            R.id.chaptersFragment -> studyNav = ""
            R.id.lookupDetailsFragment -> lookupNav = -1
        }
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        when (navController.currentDestination?.id) {
            R.id.chaptersFragment -> studyNav = ""
            R.id.lookupDetailsFragment -> lookupNav = -1
        }
        super.onBackPressed()
    }

    fun navigateToInfoPage(chapterName : String, pageName : String) {
        progressBar.visibility = View.VISIBLE
        val i = Intent(this, InfoPageActivity::class.java)
        i.putExtra("file", pageName)
        i.putExtra("chapter", chapterName)
        startActivity(i)
    }

    fun navigateToReviewQuestions(chapter : String) {
        progressBar.visibility = View.VISIBLE
        val i = Intent(this, ReviewAnswersActivity::class.java)
        i.putExtra("chapter", chapter)
        startActivity(i)
    }
}