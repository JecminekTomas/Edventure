package com.example.edventure.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.arch.fragments.BaseFragment
import com.example.edventure.EdventureApplication.Companion.appContext
import com.example.edventure.R
import kotlinx.android.synthetic.main.content_filter_tutor.place_textview
import kotlinx.android.synthetic.main.content_filter_tutor.price_max_edittext
import kotlinx.android.synthetic.main.content_filter_tutor.price_min_edittext
import kotlinx.android.synthetic.main.content_filter_tutor.rating_edittext
import kotlinx.android.synthetic.main.content_filter_tutor.rating_layout
import kotlinx.android.synthetic.main.content_filter_tutor.subject_textview
import kotlinx.android.synthetic.main.fragment_filter_teacher.*

class FilterTeacherFragment : BaseFragment() {
    override val layout: Int = R.layout.fragment_filter_teacher

    var filterRating: Double? = 0.0
    var filterPriceMin: Double? = 0.0
    var filterPriceMax: Double? = 0.0
    var filterPlace: String? = ""
    var filterSubject: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArrayAdapters()
        setInteractionsListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel_filter -> {
                onActionCancelFilter()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter_teachers, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setInteractionsListener() {
        //saveFilter.setOnClickListener { makeFilter() }

        subject_textview.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterSubject = s.toString().trim()
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
                    } else {
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
                filterRating = s.toString().toDoubleOrNull()
                if (!isFilled()) {
                    rating_layout.isErrorEnabled = false
                    saveFilterDisabled()
                }
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
        //TODO: Udělat méně dummy
        subject_textview.text?.clear()
        place_textview.text?.clear()
        rating_edittext.text?.clear()
        price_min_edittext.text?.clear()
        price_max_edittext.text?.clear()
        subject_textview.clearFocus()
        place_textview.clearFocus()
        rating_edittext.clearFocus()
        price_min_edittext.clearFocus()
        price_max_edittext.clearFocus()
    }

    /*private fun makeFilter() {
        val navController = findNavController()
        when {
            //TODO: Upravit
            rating_edittext.text!!.isNotEmpty() -> {
                navController.previousBackStackEntry?.savedStateHandle?.set("key", filterRating)
            }
            /*
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
            }*/
        }
    }*/
    private fun isFilled(): Boolean {
        return subject_textview.text!!.isNotEmpty() || place_textview.text!!.isNotEmpty() || rating_edittext.text!!.isNotEmpty()
                || price_min_edittext.text!!.isNotEmpty() || price_max_edittext.text!!.isNotEmpty()
    }

    private fun saveFilterEnabled() {
        saveFilter.setBackgroundColor(
            ContextCompat.getColor(
                appContext,
                R.color.colorPrimary
            )
        )
        saveFilter.setTextColor(
            ContextCompat.getColor(
                appContext,
                R.color.white
            )
        )
        saveFilter.isEnabled = true
    }

    private fun saveFilterDisabled() {
        saveFilter.setBackgroundColor(
            ContextCompat.getColor(
                appContext,
                R.color.colorSecondary
            )
        )
        saveFilter.isEnabled = false
    }

    private fun setArrayAdapters() {
        val places: Array<String> = resources.getStringArray(R.array.places_cz)
        val subjects: Array<String> = resources.getStringArray(R.array.subjects_cz)
        val arrayPlacesAdapter =
            ArrayAdapter(appContext, android.R.layout.simple_list_item_1, places)
        place_textview.setAdapter(arrayPlacesAdapter)
        val arrayActivitiesAdapter =
            ArrayAdapter(appContext, android.R.layout.simple_list_item_1, subjects)
        subject_textview.setAdapter(arrayActivitiesAdapter)
    }

}