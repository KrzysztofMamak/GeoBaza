package com.mamak.geobaza.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.viewmodel.ProjectDetailsSharedViewModel
import com.mamak.geobaza.ui.viewmodel.ProjectListViewModel
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
}