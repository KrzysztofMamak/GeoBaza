package com.mamak.geobaza.di.component

import android.app.Application
import com.mamak.geobaza.di.application.GeoApplication
import com.mamak.geobaza.di.module.ViewModelModule
import com.mamak.geobaza.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class,
    ContextModule::class,
    ActivityModule::class,
    FragmentModule::class,
    PicassoModule::class,
    DbModule::class,
    LocationModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun projectApiModule(projectApiModule: ApiModule): Builder

        @BindsInstance
        fun picassoModule(picassoModule: PicassoModule): Builder

        @BindsInstance
        fun dbModule(dbModule: DbModule): Builder

        @BindsInstance
        fun locationModule(locationModule: LocationModule): Builder

        fun build(): AppComponent
    }

    fun inject(geoApplication: GeoApplication)
}