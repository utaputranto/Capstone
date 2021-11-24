package com.utaputranto.core.base.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VB : ViewDataBinding>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseAdapter.Companion.BaseViewHolder<VB>>(diffUtil) {
    val items = mutableListOf<T>()

    var listener: ((view: View, position: Int, item: T) -> Unit)? = null

    abstract fun getLayout(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayout(),
            parent,
            false
        )
    )

    companion object {
        class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
                RecyclerView.ViewHolder(binding.root)
    }
}
