package com.utaputranto.capstone.ui.tv

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.utaputranto.capstone.MyApplication
import com.utaputranto.capstone.R
import com.utaputranto.capstone.databinding.FragmentTvBinding
import com.utaputranto.capstone.ui.ViewModelFactory
import com.utaputranto.capstone.ui.detail.DetailActivity
import com.utaputranto.core.base.ui.BaseFragment
import com.utaputranto.core.data.Resource
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.core.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvFragment : BaseFragment<FragmentTvBinding>({ FragmentTvBinding.inflate(it) }) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: TvViewModel by viewModels { factory }
    private val adapter by lazy { TvAdapter() }

    override fun FragmentTvBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.apply {
            rvTv.adapter = this@TvFragment.adapter
            rvTv.hasFixedSize()
            rvTv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    appbar.isSelected = recyclerView.canScrollVertically(-1)
                }
            })
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    lifecycleScope.launch {
                        viewModel.queryChannel.send(p0.toString())
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    lifecycleScope.launch {
                        viewModel.queryChannel.send(p0.toString())
                    }
                    return true
                }
            })
        }

        adapter.lifecycleOwner = this@TvFragment
        adapter.viewModel = this@TvFragment.viewModel
        adapter.listener = { _, _, item ->
            context?.startActivity<DetailActivity> {
                putExtra("KEY_DETAIL", item)
            }
        }
        adapter.favoriteListener = { item, isFavorite ->
            viewModel.setFavorite(item, isFavorite)
        }
        adapter.shareListener = { requireActivity().shareMovieTv(it) }
    }

    override fun observeViewModel() {
        observe(viewModel.tvShow, ::handleTvShows)
        observe(viewModel.search) { searchResult ->
            observe(searchResult?.asLiveData(), ::handleSearch)
        }
    }

    private fun handleTvShows(tvShows: Resource<List<MovieTv>>) {
        binding?.apply {
            when (tvShows) {
                is Resource.Loading -> {
                    errorLayout.gone()
                    loading.root.visible()
                }
                is Resource.Success -> {
                    loading.root.gone()
                    errorLayout.gone()
                    adapter.submitList(tvShows.data)
                }
                is Resource.Error -> {
                    loading.root.gone()
                    if (tvShows.data.isNullOrEmpty()) {
                        errorLayout.visible()
                        error.message.text =
                            tvShows.message ?: getString(R.string.default_error_message)
                    } else {
                        requireContext().showToast(getString(R.string.default_error_message))
                        adapter.submitList(tvShows.data)
                    }
                }
            }
        }
    }

    private fun handleSearch(tvShows: Resource<List<MovieTv>>) {
        binding?.apply {
            when (tvShows) {
                is Resource.Loading -> {
                    errorLayout.gone()
                    loading.root.visible()
                }
                is Resource.Success -> {
                    loading.root.gone()
                    errorLayout.gone()
                    if (tvShows.data.isNullOrEmpty()) rvTv.gone() else rvTv.visible()
                    adapter.submitList(tvShows.data)
                }
                is Resource.Error -> {
                    loading.root.gone()
                    if (tvShows.data.isNullOrEmpty()) {
                        errorLayout.visible()
                        rvTv.gone()
                        error.message.text =
                            tvShows.message ?: getString(R.string.default_error_message)
                    } else {
                        requireContext().showToast(getString(R.string.default_error_message))
                        adapter.submitList(tvShows.data)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onDestroyView() {
        binding?.rvTv?.adapter = null
        super.onDestroyView()
    }
}