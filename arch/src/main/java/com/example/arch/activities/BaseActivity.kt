package com.example.arch.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@SuppressLint("Registered")
@Suppress("SpellCheckingInspection")

abstract class BaseActivity: AppCompatActivity(), CoroutineScope {

    abstract val layout: Int

    private val coroutinesJob = Job()
    override val coroutineContext: CoroutineContext
        get() = coroutinesJob + Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }
}