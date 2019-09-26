package com.mamak.geobaza.di.module

import android.content.Context
import com.mamak.geobaza.ui.`interface`.ProjectListItemInterfaceImpl
import dagger.Module
import dagger.Provides

@Module(includes = [
    ContextModule::class
])
class InterfaceModule {
    @Provides
    fun projectListItemInterface(context: Context): ProjectListItemInterfaceImpl {
        return ProjectListItemInterfaceImpl(context)
    }
}