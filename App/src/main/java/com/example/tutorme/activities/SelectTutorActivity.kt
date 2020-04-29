package com.example.tutorme.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.example.tutorme.R
import kotlinx.android.synthetic.main.activity_main.*

class  SelectTutorActivity: BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectTutorActivity::class.java)
        }
    }

    private val layout: Int = R.layout.activity_select_tutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_select_tutor, menu)
        return true
    }



}