package com.mamak.geobaza.di.module

import com.mamak.geobaza.ui.activity.ProjectListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun projectListActivity(): ProjectListActivity
}