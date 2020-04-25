package com.example.tutorme.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.tutorme.R
import com.example.tutorme.sharedpreferences.SharedPreferencesManager

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (!SharedPreferencesManager.isRunForFirstTime(this)) {
            startActivity(SelectTutorActivity.createIntent(this))
            finish()
        }
    }

    fun continueToApp (view: View) {
        SharedPreferencesManager.saveFirstRun(this)
        startActivity(SelectTutorActivity.createIntent(this))
        finish()
    }
}

// TODO: New branch develop 👌, feature - maybe later?
// TODO: Watch some stuff about fragments... In Android Studio there is bunch of them.
