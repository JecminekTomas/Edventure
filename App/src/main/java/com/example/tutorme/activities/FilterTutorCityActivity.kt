package com.example.tutorme.activities

import android.content.Context
import android.content.Intent

class FilterTutorCityActivity: BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterTutorCityActivity::class.java)
        }
    }
}