package com.example.edventure.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.arch.BaseActivity
import com.example.edventure.R

class BottomNavigationActivity : BaseActivity(){

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, BottomNavigationActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_bottom_navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}