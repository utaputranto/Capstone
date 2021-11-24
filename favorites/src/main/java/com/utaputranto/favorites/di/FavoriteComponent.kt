package com.utaputranto.favorites.di

import com.utaputranto.capstone.di.AppModule
import com.utaputranto.core.di.CoreComponent
import com.utaputranto.favorites.ui.FavoriteTvFragment
import com.utaputranto.favorites.ui.FavoritesFragment
import dagger.Component
import com.utaputranto.favorites.ui.FavoriteMovieFragment

@FavoriteAppScope
@Component(dependencies = [CoreComponent::class],
modules = [AppModule::class, FavoriteViewModelModule::class])
interface FavoriteComponent {
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: FavoriteMovieFragment)
    fun inject(fragment: FavoriteTvFragment)
}