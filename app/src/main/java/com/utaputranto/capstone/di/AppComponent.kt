package com.utaputranto.capstone.di

import com.utaputranto.capstone.ui.MainActivity
import com.utaputranto.capstone.ui.detail.DetailActivity
import com.utaputranto.capstone.ui.movie.MovieFragment
import com.utaputranto.capstone.ui.tv.TvFragment
import com.utaputranto.core.di.CoreComponent
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
    fun inject(fragment: MovieFragment)
    fun inject(fragment: TvFragment)
}