package com.utaputranto.capstone.ui.tv

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.utaputranto.capstone.R
import com.utaputranto.core.base.ui.BaseAdapter
import com.utaputranto.core.databinding.ItemMovieTvBinding
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.core.utils.observe

class  TvAdapter : BaseAdapter<MovieTv, ItemMovieTvBinding>(DIFF_CALLBACK) {

    lateinit var viewModel: TvViewModel
    lateinit var lifecycleOwner: LifecycleOwner

    var favoriteListener: ((item: MovieTv, isFavorite: Boolean) -> Unit)? = null
    var shareListener: ((item: MovieTv) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_movie_tv

    override fun onBindViewHolder(holder: BaseAdapter.Companion.BaseViewHolder<ItemMovieTvBinding>, position: Int) {
        val item = getItem(position) ?: return
        holder.apply {
            binding.root.setOnClickListener { listener?.invoke(it, position, item) }
            lifecycleOwner.observe(viewModel.isFavorite(item)) { isFavorite ->
                binding.cbIsFav.setOnClickListener {
                    favoriteListener?.invoke(item, isFavorite)
                }
                binding.apply {
                    setVariable(BR.isFavorite, isFavorite)
                    executePendingBindings()
                }
            }
            binding.btnShare.setOnClickListener { shareListener?.invoke(item) }
            binding.apply {
                setVariable(BR.item, item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieTv>() {
            override fun areContentsTheSame(oldItem: MovieTv, newItem: MovieTv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: MovieTv, newItem: MovieTv): Boolean {
                return oldItem == newItem
            }
        }
    }
}