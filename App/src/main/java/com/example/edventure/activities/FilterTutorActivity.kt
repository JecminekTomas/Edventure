package com.example.edventure.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.arch.activities.BaseActivity
import com.example.edventure.R
import kotlinx.android.synthetic.main.activity_select_tutor.*
import kotlinx.android.synthetic.main.content_filter_tutor.*

class FilterTutorActivity : BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterTutorActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_filter_tutor
    var filterRating: Double? = 0.0
    var filterPriceMin: Double? = 0.0
    var filterPriceMax: Double? = 0.0
    var filterPlace: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        setArrayAdapters()
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

    private fun setInteractionsListener() {
        //saveFilter.setOnClickListener { makeFilter() }

        place_textview.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterPlace = s.toString().trim()
                if (isFilled()) {
                    saveFilterEnabled()
                } else {
                    saveFilterDisabled()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        rating_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterRating = s.toString().toDoubleOrNull()
                if (isFilled()) {
                    if (filterRating!! in 0.0..5.0) {
                        saveFilterEnabled()
                    }
                    else {
                        rating_layout.isErrorEnabled = true
                        rating_layout.error = getString(R.string.range)
                        saveFilterDisabled()
                    }
                } else {
                    saveFilterDisabled()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        price_min_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterPriceMin = s.toString().toDoubleOrNull()
                if (isFilled()) {
                    saveFilterEnabled()
                } else {
                    saveFilterDisabled()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        price_max_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterPriceMax = s.toString().toDoubleOrNull()
                if (isFilled()) {
                    saveFilterEnabled()
                } else {
                    saveFilterDisabled()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


    }

    private fun onActionCancelFilter() {

    }

    private fun makeFilter() {
        val returnIntent = Intent()
        when {
            rating_edittext.text!!.isNotEmpty() -> {
                returnIntent.putExtra("filter_type", "filter_rating")
                returnIntent.putExtra("filter_rating", filterRating)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            place_textview.text!!.isNotEmpty() -> {
                returnIntent.putExtra("filter_type", "filter_place")
                returnIntent.putExtra("filter_place", filterPlace)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            price_min_edittext.text!!.isNotEmpty() -> {
                returnIntent.putExtra("filter_type", "filter_price_min")
                returnIntent.putExtra("filter_price_min", filterPriceMin)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            price_max_edittext.text!!.isNotEmpty() -> {
                returnIntent.putExtra("filter_type", "filter_price_max")
                returnIntent.putExtra("filter_price_max", filterPriceMin)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    private fun isFilled(): Boolean {
        return place_textview.text!!.isNotEmpty() || rating_edittext.text!!.isNotEmpty()
                || price_min_edittext.text!!.isNotEmpty() || price_max_edittext.text!!.isNotEmpty()
    }

    private fun saveFilterEnabled() {
        /*saveFilter.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        saveFilter.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        saveFilter.isEnabled = true*/
    }

    private fun saveFilterDisabled() {
        /*saveFilter.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorSecondary
            )
        )
        saveFilter.isEnabled = false*/
    }

    private fun setArrayAdapters() {
        val places: Array<String> = resources.getStringArray(R.array.places_cz)
        val subjects: Array<String> = resources.getStringArray(R.array.subjects_cz)
        val arrayPlacesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, places)
        place_textview.setAdapter(arrayPlacesAdapter)
        val arrayActivitiesAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects)
        subject_textview.setAdapter(arrayActivitiesAdapter)
    }

}