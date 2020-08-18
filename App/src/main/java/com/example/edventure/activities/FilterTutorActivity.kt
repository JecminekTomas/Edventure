package com.example.edventure.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.arch.BaseActivity
import com.example.edventure.R
import kotlinx.android.synthetic.main.activity_select_tutor.toolbar
import kotlinx.android.synthetic.main.content_filter_tutor.*

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

        val places: Array<String> = resources.getStringArray(R.array.places_cz)
        val subjects: Array<String> = resources.getStringArray(R.array.subjects_cz)

        val arrayPlacesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, places)
        filter_place_textview.setAdapter(arrayPlacesAdapter)
        val arrayActivitiesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects)
        filter_subject_textview.setAdapter(arrayActivitiesAdapter)

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