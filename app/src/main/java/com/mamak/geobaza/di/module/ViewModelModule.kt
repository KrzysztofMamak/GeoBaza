package com.mamak.geobaza.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.viewmodel.*
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
    @ViewModelKey(ProjectDetailsSharedViewModel::class)
    protected abstract fun projectDetailsSharedViewModel(projectDetailsSharedViewModel: ProjectDetailsSharedViewModel): ViewModel

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
    @ViewModelKey(ProjectListViewModelNew::class)
    protected abstract fun projectListViewModelNew(projectListViewModelNew: ProjectListViewModelNew): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    protected abstract fun settingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    protected abstract fun statisticsViewModel(statisticsViewModel: StatisticsViewModel): ViewModel
}