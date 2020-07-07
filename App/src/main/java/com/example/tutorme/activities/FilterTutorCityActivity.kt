package com.example.tutorme.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arch.BaseMVVMActivity
import com.example.tutorme.R
import com.example.tutorme.viewmodels.FilterTutorCityVM
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.content_filter_tutor_city.*

class FilterTutorCityActivity: BaseMVVMActivity<FilterTutorCityVM>(FilterTutorCityVM::class.java) {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterTutorCityActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_filter_tutor

    private var citiesList: MutableList<String> = viewModel.findCities()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityAdapter = CityAdapter()
        layoutManager = LinearLayoutManager(this)
        filterTutorCityRecyclerView.adapter = cityAdapter
        filterTutorCityRecyclerView.layoutManager = layoutManager
    }


    inner class CityAdapter : RecyclerView.Adapter<CityAdapter.CityVieHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityVieHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tutor_filter_city, parent, false)
            return CityVieHolder(view)
        }

        override fun getItemCount() = citiesList.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: CityVieHolder, position: Int) {
            val city = citiesList[position]
            holder.cityName.text = city
        }

        /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
        inner class CityVieHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityName: TextView = view.findViewById(R.id.cityName)
        }
    }

}