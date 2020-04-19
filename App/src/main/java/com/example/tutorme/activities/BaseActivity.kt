package com.example.tutorme.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@SuppressLint("Registered")
@Suppress("SpellCheckingInspection")

open class BaseActivity: AppCompatActivity(), CoroutineScope {

    private val coroutinesJob = Job()
    override val coroutineContext: CoroutineContext
        get() = coroutinesJob + Dispatchers.IO

}