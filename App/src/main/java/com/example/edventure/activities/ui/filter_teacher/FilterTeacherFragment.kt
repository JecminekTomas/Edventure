package com.example.edventure.activities.ui.filter_teacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edventure.R

class FilterTeacherFragment : Fragment() {

    companion object {
        fun newInstance() = FilterTeacherFragment()
    }

    private lateinit var viewModel: FilterTeacherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.filter_teacher_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FilterTeacherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}