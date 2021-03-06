package com.mamak.geobaza.di.application

import android.app.Application
import com.mamak.geobaza.di.component.DaggerAppComponent
import com.mamak.geobaza.di.module.*

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GeoApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .projectApiModule(ApiModule())
            .picassoModule(PicassoModule())
            .dbModule(DbModule())
            .locationModule(LocationModule())
            .build()
            .inject(this)
    }
}