package com.example.edventure.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.example.arch.BaseActivity
import com.example.edventure.R
import kotlinx.android.synthetic.main.activity_select_tutor.toolbar
import kotlinx.android.synthetic.main.content_filter_tutor.*

class FilterTutorActivity: BaseActivity() {

    private lateinit var subjects: MutableList<String>
    private lateinit var cities: MutableList<String>
    private var prices = arrayOfNulls<Double>(2)
    private var onlineLecture: Boolean = false
    private var groupLecture: Boolean = false
    private var homeLecture: Boolean = false

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

        setInteractionsListener()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel_filter -> {
                onActionCancelFilter()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setInteractionsListener(){
    }

    private fun onActionCancelFilter(){

    }

}