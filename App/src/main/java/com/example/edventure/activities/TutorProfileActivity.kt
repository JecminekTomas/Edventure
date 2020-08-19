package com.example.edventure.activities

import android.content.Context
import android.content.Intent
import com.example.edventure.R

class TutorProfileActivity: com.example.arch.BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, TutorProfileActivity::class.java)
        }
    }
    override val layout: Int = R.layout.activity_tutor_profile
}