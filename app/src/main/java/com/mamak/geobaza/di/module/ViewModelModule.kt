package com.mamak.geobaza.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProjectListViewModel::class)
    protected abstract fun projectListViewModel(projectListViewModel: ProjectListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProjectDetailsActivityViewModel::class)
    protected abstract fun projectDetailsActivityViewModel(projectDetailsActivityViewModel: ProjectDetailsActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    protected abstract fun registrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordResetViewModel::class)
    protected abstract fun passwordResetViewModel(passwordResetViewModel: PasswordResetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    protected abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    protected abstract fun settingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    protected abstract fun statisticsViewModel(statisticsViewModel: StatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    protected abstract fun mainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TravelPlannerViewModel::class)
    protected abstract fun travelPlannerViewModel(travelPlannerViewModel: TravelPlannerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProjectOverviewViewModel::class)
    protected abstract fun projectOverviewViewModel(projectOverviewViewModel: ProjectOverviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    protected abstract fun mapViewModel(mapViewModel: MapViewModel): ViewModel
}