package com.example.arch.fragments

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.arch.BaseMVVMFragmentVM

abstract class BaseMVVMFragment<B: ViewDataBinding, VM : BaseMVVMFragmentVM>( @LayoutRes layout: Int, viewModelClass: Class<VM>) : BaseFragment<B>(layout) {
    protected open val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(viewModelClass)
    }
}