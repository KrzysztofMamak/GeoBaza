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
        setOnClick()
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
        setCheckpoint(
            sb_project_checkpoints.iv_first_step_foreground,
            sb_project_checkpoints.iv_first_step_background,
            project.isProcessed)
        setCheckpoint(
            sb_project_checkpoints.iv_second_step_foreground,
            sb_project_checkpoints.iv_second_step_background,
            project.isMarked)
        setCheckpoint(
            sb_project_checkpoints.iv_third_step_foreground,
            sb_project_checkpoints.iv_third_step_background,
            project.isMeasured)
        setCheckpoint(
            sb_project_checkpoints.iv_fourth_step_foreground,
            sb_project_checkpoints.iv_fourth_step_background,
            project.isFinished)
    }

//    TODO refactor
    private fun setCheckpoint(foreground: ImageView, background: ImageView, isChecked: Boolean) {
        val foregroundColor = if (isChecked) R.color.white else R.color.colorSecondaryLight
        val drawable = if (isChecked) R.drawable.item_circle_full else R.drawable.item_circle
        context?.let {
            ImageViewCompat.setImageTintList(
                foreground,
                ColorStateList.valueOf(it.getColor(foregroundColor)))
        }
        background.setImageDrawable(context?.getDrawable(drawable))
    }

    private fun setOnClick() {
        iv_edit_project.setOnClickListener {
//            val intent = Intent(activity, ProjectDataEditActivity::class.java)
//            intent.putExtra(EXTRA_PROJECT_NUMBER, project.number)
//            startActivity(intent)
        }
    }
}