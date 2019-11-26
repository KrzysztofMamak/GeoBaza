package com.mamak.geobaza.di.module

import com.mamak.geobaza.ui.activity.ProjectDataEditActivity
import com.mamak.geobaza.ui.activity.ProjectDetailsActivity
import com.mamak.geobaza.ui.activity.ProjectListActivity
import com.mamak.geobaza.ui.activity.RegistrationLoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun projectListActivity(): ProjectListActivity

    @ContributesAndroidInjector
    abstract fun projectDetailsActivity(): ProjectDetailsActivity

    @ContributesAndroidInjector
    abstract fun registrationLoginActivity(): RegistrationLoginActivity

    @ContributesAndroidInjector
    abstract fun projectDataEditActivity(): ProjectDataEditActivity
}