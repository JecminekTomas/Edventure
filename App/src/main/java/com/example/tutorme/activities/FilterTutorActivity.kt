package com.example.tutorme.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.arch.BaseActivity
import com.example.tutorme.R
import kotlinx.android.synthetic.main.activity_select_tutor.toolbar

class FilterTutorActivity: BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterTutorActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_filter_tutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}