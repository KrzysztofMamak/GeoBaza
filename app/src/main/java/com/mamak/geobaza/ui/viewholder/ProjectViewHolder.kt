package com.mamak.geobaza.ui.viewholder

import android.location.Location
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Point
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.communication.ProjectItemInterface
import com.mamak.geobaza.utils.manager.LocationManager.calculateDistance
import kotlinx.android.synthetic.main.item_list_project_expandable.view.*

class ProjectViewHolder(
    itemView: View,
    private val projectItemInterface: ProjectItemInterface
) : RecyclerView.ViewHolder(itemView) {
    private var isExpanded = false

    fun bind(project: Project, location: Location?) {
        setProjectData(project)
        setDistance(project.pointList[0], location)
        setNavigationIconOnClick(project.pointList[0].x, project.pointList[0].y)
        setMapIconOnClick(project.number)
        setDetailsIconOnClick(project.number)
    }

    private fun setProjectData(project: Project) {
        itemView.apply {
            tv_project_number.text = project.number.toString()
            tv_town.text = project.town
            tv_street.text = project.street
            tv_description.text = project.description
        }
    }

    private fun setDistance(point: Point, location: Location?) {
        itemView.apply {
            tv_distance.text = context.getString(R.string.unit_km_with_value)// TODO , distance)
            visibility = View.VISIBLE
        }
    }

    fun setExpanded() {
        itemView.container_actions.visibility = if (isExpanded) View.GONE else View.VISIBLE
        isExpanded = !isExpanded
    }

    private fun setNavigationIconOnClick(x: Double, y: Double) {
        itemView.iv_action_navigation.setOnClickListener {
            projectItemInterface.navigate(x, y)
        }
    }

    private fun setMapIconOnClick(projectNumber: Int) {
        itemView.iv_action_map.setOnClickListener {
            projectItemInterface.showMap(projectNumber)
        }
    }

    private fun setDetailsIconOnClick(projectNumber: Int) {
        itemView.iv_action_details.setOnClickListener {
            projectItemInterface.showDetails(projectNumber)
        }
    }
}