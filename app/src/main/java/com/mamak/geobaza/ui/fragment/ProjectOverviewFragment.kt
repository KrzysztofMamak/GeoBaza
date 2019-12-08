package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.adapter.ProjectDataAdapter
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.ProjectDetailsSharedViewModel
import com.mamak.geobaza.utils.manager.MappingManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_project_overview.*
import javax.inject.Inject

class ProjectOverviewFragment(private val project: Project) : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var projectDetailsSharedViewModel: ProjectDetailsSharedViewModel

    private val projectDataAdapter = ProjectDataAdapter()
    private var inEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        setStepbar()
        setProjectDataAdapter(project)
        setOnClick()
    }

    private fun initViewModel() {
        projectDetailsSharedViewModel = viewModelFactory.create(ProjectDetailsSharedViewModel::class.java)
    }

    private fun initRecycler() {
        rv_project_data.apply {
            adapter = projectDataAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setProjectDataAdapter(project: Project?) {
        projectDataAdapter.setProperties(MappingManager.projectToFieldList(project))
    }

    private fun setStepbar() {
        sb_project_checkpoints.setState(
            project.isProcessed,
            project.isMarked,
            project.isMeasured,
            project.isFinished
        )
    }

    private fun updateProject(project: Project) {
        projectDetailsSharedViewModel.apply {
            updateProject(project)
            getProjectUpdateLiveData().observe(this@ProjectOverviewFragment, Observer { resource ->
                if (resource.isLoading) {
                    //loading
                } else if (resource.isSuccess) {
                    if (resource.data != null) {
                        if (resource.data.isSuccessful) {
                            //success
                            Log.d("TAG", resource.data.toString())
                        } else {
                            //error
                        }
                    }
                } else {
                    //error
                }
            })
        }
    }

    private fun setOnClick() {
        iv_edit_project.setOnClickListener {
            if (inEditMode) {
                updateProject()
            } else {

            }
        }
    }

    private fun switchMode(currentlyInEditMode: Boolean) {
        if (currentlyInEditMode) {

            inEditMode = false
        } else {

            inEditMode = true
        }
    }

    private fun updateProject() {
        updateProject(project)
    }
}