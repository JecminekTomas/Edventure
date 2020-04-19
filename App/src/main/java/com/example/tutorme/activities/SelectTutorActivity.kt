package com.example.tutorme.activities

import android.content.Context
import android.content.Intent

class SelectTutorActivity: BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectTutorActivity::class.java)
        }
    }




}