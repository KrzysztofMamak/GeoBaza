package com.mamak.geobaza.di.module

import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides

@Module(includes = [
    ContextModule::class
])
class LocationModule {
    @Provides
    fun locationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}