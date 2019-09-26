package com.mamak.geobaza.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.`interface`.ProjectListItemInterface
import kotlinx.android.synthetic.main.item_list_project.view.*

class ProjectListViewHolder(
    itemView: View,
    private val projectListItemCommunication: ProjectListItemInterface
) : RecyclerView.ViewHolder(itemView) {
    fun bindView(project: Project) {
        setProjectData(project)
        setNavigationIcon(project)
        setItemViewOnClick(project.number)
    }

    private fun setProjectData(project: Project) {
        itemView.apply {
            tv_town.text = project.town
            tv_street.text = project.street
            tv_description.text = project.description
            tv_project_number.text = project.number.toString()
        }
    }

    private fun setNavigationIcon(project: Project) {
        itemView.iv_navigation.setOnClickListener {
            projectListItemCommunication.openGoogleMaps(
                project.pointList?.get(0)?.x,
                project.pointList?.get(0)?.y
            )
        }
    }

    private fun setItemViewOnClick(number: Int?) {
        itemView.setOnClickListener {
            projectListItemCommunication.openProjectDetails(number)
        }
    }
}