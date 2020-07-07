package com.example.tutorme.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.example.arch.BaseActivity
import com.example.tutorme.R
import kotlinx.android.synthetic.main.activity_select_tutor.toolbar
import kotlinx.android.synthetic.main.content_add_edit_tutor.*
import kotlinx.android.synthetic.main.content_filter_tutor.*
import java.text.NumberFormat
import java.util.*

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

        price_slider.setLabelFormatter { value: Float ->
            return@setLabelFormatter String.format("%.0f Kč", value)
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
        chooseSubject.setOnClickListener{onActionChooseSubject()}
    }

    private fun onActionCancelFilter(){

    }

    private fun onActionChooseSubject(){
        startActivity(FilterTutorCityActivity.createIntent(this))
    }
}