package com.mamak.geobaza.di.component

import android.app.Application
import com.mamak.geobaza.di.controller.AppController
import com.mamak.geobaza.di.module.ViewModelModule
import com.mamak.geobaza.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ProjectApiModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class,
    InterfaceModule::class,
    ContextModule::class,
    ActivityModule::class,
    PicassoModule::class,
    DbModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun projectApiModule(projectApiModule: ProjectApiModule): Builder

        @BindsInstance
        fun interfaceModule(interfaceModule: InterfaceModule): Builder

        @BindsInstance
        fun picassoModule(picassoModule: PicassoModule): Builder

        @BindsInstance
        fun dbModule(dbModule: DbModule): Builder

        fun build(): AppComponent
    }

    fun inject(appController: AppController)
}