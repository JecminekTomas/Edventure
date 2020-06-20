package com.example.tutorme

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class TutorApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext!!
    }

    companion object {

        /**
         * Returns app context
         * @return
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
            // private setter
            private set
    }
}