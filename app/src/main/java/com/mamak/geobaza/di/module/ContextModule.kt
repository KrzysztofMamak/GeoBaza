package com.mamak.geobaza.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ContextModule {
    @Singleton
    @Binds
    abstract fun context(appInstance: Application): Context

//    @Singleton
//    @Binds
//    abstract fun application(appInstance: Application): Application
}