package com.utaputranto.capstone

import android.app.Application
import com.utaputranto.capstone.di.AppComponent
import com.utaputranto.capstone.di.DaggerAppComponent
import com.utaputranto.core.di.CoreComponent
import com.utaputranto.core.di.DaggerCoreComponent

class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}