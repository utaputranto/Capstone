package com.utaputranto.core.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding>(val viewBinder: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBinder(layoutInflater)
        setContentView(binding.root)

        binding.onCreate(savedInstanceState)
        observeViewModel()
    }

    protected abstract fun B.onCreate(savedInstanceState: Bundle?)

    protected abstract fun observeViewModel()
}