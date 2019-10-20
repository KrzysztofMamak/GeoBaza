package com.mamak.geobaza.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Point
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.`interface`.ProjectListItemInterface
import com.mamak.geobaza.utils.LocationManager.calculateDistance
import kotlinx.android.synthetic.main.item_list_project.view.*
import java.text.DecimalFormat

class ProjectListViewHolder(
    itemView: View,
    private val projectListItemCommunication: ProjectListItemInterface
) : RecyclerView.ViewHolder(itemView) {
    fun bindView(project: Project) {
        setProjectData(project)
        setDistance(project.pointList[0])
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
                project.pointList[0].x,
                project.pointList[0].y
            )
        }
    }

    private fun setItemViewOnClick(number: Int?) {
        itemView.setOnClickListener {
            projectListItemCommunication.openProjectDetails(number)
        }
    }

    private fun setDistance(point: Point) {
        calculateDistance(point)?.let {
            itemView.tv_distance.text = itemView.context.getString(R.string.unit_km_with_value, it)
            itemView.visibility = View.VISIBLE
        }
    }
}