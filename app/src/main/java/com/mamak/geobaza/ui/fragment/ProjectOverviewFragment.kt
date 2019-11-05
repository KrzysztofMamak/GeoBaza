package com.mamak.geobaza.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.adapter.ProjectDataAdapter
import com.mamak.geobaza.utils.manager.MappingManager
import kotlinx.android.synthetic.main.fragment_project_overview.*
import kotlinx.android.synthetic.main.layout_step_bar.view.*

class ProjectOverviewFragment(private val project: Project) : Fragment() {
    private val projectDataAdapter = ProjectDataAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        setStepbar()
        setProjectDataAdapter(project)
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
        setCheckPoint(
            sb_project_checkpoints.iv_first_step_foreground,
            sb_project_checkpoints.iv_first_step_background,
            project.isProcessed)
        setCheckPoint(
            sb_project_checkpoints.iv_second_step_foreground,
            sb_project_checkpoints.iv_second_step_background,
            project.isMarked)
        setCheckPoint(
            sb_project_checkpoints.iv_third_step_foreground,
            sb_project_checkpoints.iv_third_step_background,
            project.isMeasured)
        setCheckPoint(
            sb_project_checkpoints.iv_fourth_step_foreground,
            sb_project_checkpoints.iv_fourth_step_background,
            project.isFinished)
    }

    private fun setCheckPoint(foreground: ImageView, background: ImageView, isChecked: Boolean) {
        if (isChecked) {
            context?.let {
                ImageViewCompat.setImageTintList(
                    foreground,
                    ColorStateList.valueOf(it.getColor(R.color.colorTextOnPrimary)))
            }
            background.setImageDrawable(context?.getDrawable(R.drawable.item_circle_full))
        } else {
            context?.let {
                ImageViewCompat.setImageTintList(
                    foreground,
                    ColorStateList.valueOf(it.getColor(R.color.colorSecondaryLight)))
            }
            background.setImageDrawable(context?.getDrawable(R.drawable.item_circle))
        }
    }
}