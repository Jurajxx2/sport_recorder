package com.example.sportrecorder.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<V: ViewModel, B: ViewDataBinding>: AppCompatActivity() {

    protected abstract val layout: Int
    lateinit var binding: B
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupActivity()
    }

    private fun setupBinding() {
        val binding = DataBindingUtil.setContentView<B>(this, layout)
        this.binding = binding
        binding.executePendingBindings()
    }

    abstract fun setupActivity()
}