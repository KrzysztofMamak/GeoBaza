package com.mamak.geobaza.di.controller

import android.app.Application
import com.mamak.geobaza.di.component.DaggerAppComponent
import com.mamak.geobaza.di.module.DbModule
import com.mamak.geobaza.di.module.InterfaceModule
import com.mamak.geobaza.di.module.PicassoModule
import com.mamak.geobaza.di.module.ProjectApiModule

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AppController : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .projectApiModule(ProjectApiModule())
            .interfaceModule(InterfaceModule())
            .picassoModule(PicassoModule())
            .dbModule(DbModule())
            .build()
            .inject(this)
    }
}