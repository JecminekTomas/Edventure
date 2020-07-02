package com.example.tutorme.activities

import android.content.Context
import android.content.Intent
import com.example.arch.BaseActivity
import com.example.tutorme.R

class FilterTutorActivity: BaseActivity() {



    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterTutorActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_filter_tutor
}