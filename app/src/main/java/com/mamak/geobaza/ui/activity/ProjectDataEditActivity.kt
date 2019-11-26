package com.mamak.geobaza.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.viewmodel.ProjectDataEditViewModel
import com.mamak.geobaza.utils.constans.AppConstans.EXTRA_PROJECT_NUMBER
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProjectDataEditActivity: BaseActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  projectDataEditViewModel: ProjectDataEditViewModel

    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_data_edit)
        AndroidInjection.inject(this)
        initViewModel()
        setCurrentProject()
    }

    private fun initViewModel() {
        projectDataEditViewModel = viewModelFactory.create(projectDataEditViewModel::class.java)
    }

    private fun setCurrentProject() {
        val projectNumber = intent.getIntExtra(EXTRA_PROJECT_NUMBER, 1)
        projectDataEditViewModel.getProjectFromDb(projectNumber)
        projectDataEditViewModel.getProjectLiveData().observe(this, Observer {
            project = it
            setProjectDataFields()
        })
    }

    private fun setProjectDataFields() {

    }
}