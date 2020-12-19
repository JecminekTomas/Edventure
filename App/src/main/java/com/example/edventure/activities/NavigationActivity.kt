package com.example.edventure.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.arch.activities.BaseActivity
import com.example.edventure.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_select_tutor.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NavigationActivity : BaseActivity() {

    private var currentNavController: LiveData<NavController>? = null
    override val layout: Int = R.layout.activity_navigation
    lateinit var bottomNavigationView: BottomNavigationView
    private val listener = NavController.OnDestinationChangedListener{_, destination, _ ->
        if(destination.id == R.id.filter_teacher) {
            hideBottomNavigationBar()
        } else {
            showBottomNavigationBar()
        }
    }


    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, NavigationActivity::class.java)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }


    private fun hideBottomNavigationBar() {
        bottomNavigationView.visibility = GONE
    }

    private fun showBottomNavigationBar() {
        bottomNavigationView.visibility = VISIBLE
    }

    private fun setupBottomNavigationBar() {
        bottomNavigationView = findViewById(R.id.bottom_nav)

        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.search,
            R.navigation.chat,
            R.navigation.calendar,
            R.navigation.profile
        )

        val navController = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        navController.observe(this, Observer { controller ->
            setupActionBarWithNavController(controller)
            controller.addOnDestinationChangedListener(listener)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}