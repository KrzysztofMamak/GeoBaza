package com.mamak.geobaza.di.module

import com.mamak.geobaza.ui.activity.ProjectDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun projectDetailsActivity(): ProjectDetailsActivity
}