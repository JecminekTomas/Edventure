package com.example.arch.fragments

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseMVVMFragment<out VM : ViewModel>(private val viewModelClass: Class<VM>): BaseFragment() {
    protected open val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(viewModelClass)
    }
}