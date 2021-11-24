package com.utaputranto.core.di

import com.utaputranto.core.data.source.AppRepositoryImpl
import com.utaputranto.core.domain.repository.AppRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {
    @Suppress("unused")
    @Binds
    abstract fun provideRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}