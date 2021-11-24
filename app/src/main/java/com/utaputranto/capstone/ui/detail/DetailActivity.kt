package com.utaputranto.capstone.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.utaputranto.capstone.MyApplication
import com.utaputranto.capstone.R
import com.utaputranto.capstone.databinding.ActivityDetailBinding
import com.utaputranto.capstone.ui.ViewModelFactory
import com.utaputranto.core.base.ui.BaseActivity
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.core.utils.observe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: DetailViewModel by viewModels { factory }

    @ExperimentalCoroutinesApi
    override fun ActivityDetailBinding.onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this@DetailActivity)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        val movieTv = intent.getParcelableExtra<MovieTv>("KEY_DETAIL")
        movieTv?.let {
            viewModel.setSelectedItem(it)
        }
    }

    override fun observeViewModel() {
        observe(viewModel.movieTvItem) { binding.item = it }
        observe(viewModel.isFavorite, ::setFavoriteState)
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        binding.fabFav.setOnClickListener {
            viewModel.setToFavorite(isFavorite)
        }
        binding.fabFav.setImageDrawable(
            ContextCompat.getDrawable(
                this@DetailActivity,
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}