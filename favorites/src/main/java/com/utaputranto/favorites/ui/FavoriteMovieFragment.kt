package com.utaputranto.favorites.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import com.utaputranto.capstone.ui.ViewModelFactory
import com.utaputranto.core.base.ui.BaseFragment
import com.utaputranto.core.di.CoreComponent
import com.utaputranto.core.di.DaggerCoreComponent
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.capstone.R
import com.utaputranto.capstone.ui.detail.DetailActivity
import com.utaputranto.core.utils.*
import com.utaputranto.favorites.databinding.FragmentFavoriteMovieBinding
import com.utaputranto.favorites.di.DaggerFavoriteComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class FavoriteMovieFragment : BaseFragment<FragmentFavoriteMovieBinding>({ FragmentFavoriteMovieBinding.inflate(it) }) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(requireActivity())
    }
    private val viewModel: FavoriteViewModel by viewModels { factory }
    private val adapter by lazy { FavoriteMovieAdapter() }

    override fun FragmentFavoriteMovieBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.rvFavoriteMovie?.adapter = this@FavoriteMovieFragment.adapter
        adapter.lifecycleOwner = this@FavoriteMovieFragment
        adapter.viewModel = this@FavoriteMovieFragment.viewModel
        adapter.listener = { _, _, item ->
            context?.startActivity<DetailActivity> {
                putExtra("KEY_DETAIL", item)
            }
        }
        adapter.favoriteListener = { item, isFavorite ->
            viewModel.setToFavorite(item, isFavorite)
            binding?.apply {
                Snackbar.make(root, getString(R.string.deleted_favorite, getString(R.string.movie)), Snackbar.LENGTH_LONG).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.setToFavorite(item, false)
                    }
                    show()
                }
            }
        }
        adapter.shareListener = { requireActivity().shareMovieTv(it) }
    }

    override fun observeViewModel() {
        observe(viewModel.favoriteMovies, ::handleFavMovies)
    }

    private fun handleFavMovies(favMovies: PagedList<MovieTv>) {
        if (!favMovies.isNullOrEmpty()) {
            binding?.emptyFavorite?.root?.gone()
            binding?.rvFavoriteMovie?.visible()
            adapter.submitList(favMovies)
        } else {
            binding?.emptyFavorite?.root?.visible()
            binding?.rvFavoriteMovie?.gone()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder().coreComponent(coreComponent).build().inject(this)
    }
}