package com.utaputranto.capstone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utaputranto.capstone.ui.ViewModelFactory
import com.utaputranto.capstone.ui.detail.DetailViewModel
import com.utaputranto.capstone.ui.movie.MovieViewModel
import com.utaputranto.capstone.ui.tv.TvViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvViewModel::class)
    abstract fun bindTvViewModel(viewModel: TvViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}