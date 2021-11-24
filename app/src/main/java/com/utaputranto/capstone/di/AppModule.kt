package com.utaputranto.capstone.di

import com.utaputranto.core.domain.usecase.MovieTvInteractor
import com.utaputranto.core.domain.usecase.MovieTvUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Suppress("unused")
    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieTvInteractor): MovieTvUseCase
}