package com.mamak.geobaza.di.module

import com.mamak.geobaza.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeFilterDialogFragment(): FilterDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectMapFragment(): ProjectMapFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectSketchFragment(): ProjectSketchFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributePasswordResetFragment(): PasswordResetFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectListFragment(): ProjectListFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeStatisticsFragment(): StatisticsFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectOverviewFragmentNew(): ProjectOverviewFragment

    @ContributesAndroidInjector
    abstract fun contributeTravelPlannerFragment(): TravelPlannerFragment

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment
}
