package com.utaputranto.core.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(val viewBinder: (LayoutInflater) -> B) : Fragment() {
    var binding: B? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = viewBinder(inflater).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.onViewCreated(savedInstanceState)
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected abstract fun B.onViewCreated(savedInstanceState: Bundle?)

    protected abstract fun observeViewModel()
}