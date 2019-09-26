package com.mamak.geobaza.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.`interface`.ProjectListItemInterface
import com.mamak.geobaza.ui.viewholder.ProjectListViewHolder
import com.mamak.geobaza.utils.ProjectListManager

class ProjectListAdapter(private val projectListItemCommunication: ProjectListItemInterface)
        : RecyclerView.Adapter<ProjectListViewHolder>(), Filterable {
    private var projects = mutableListOf<Project>()
    private var filteredProjects = mutableListOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        return ProjectListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_project, parent, false),
            projectListItemCommunication
        )
    }

    override fun getItemCount(): Int {
        return filteredProjects.size
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
        holder.bindView(filteredProjects[position])
    }

    fun setProjects() {
        this.projects = ProjectListManager.getRequiredProjects()
        this.filteredProjects = ProjectListManager.getRequiredProjects()
        notifyDataSetChanged()
    }

    fun filterProjects(
            areaFilterType: ProjectListManager.AreaFilterType,
            stateFilterType: ProjectListManager.StateFilterType,
            sortType: ProjectListManager.SortType,
            orderType: ProjectListManager.OrderType) {
        ProjectListManager.setAttributes(areaFilterType, stateFilterType, sortType, orderType)
        filteredProjects = ProjectListManager.getRequiredProjects()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.toLowerCase()
                val results = FilterResults()
                if (!queryString.isNullOrEmpty()) {
                    val filterList = mutableListOf<Project>()
                    for (project in projects) {
                        project.area?.toLowerCase()?.let {
                            if (it.contains(queryString)) {
                                filterList.add(project)
                            }
                        }
                        project.town?.toLowerCase()?.let {
                            if (it.contains(queryString)) {
                                filterList.add(project)
                            }
                        }
                        project.street?.toLowerCase()?.let {
                            if (it.contains(queryString)) {
                                filterList.add(project)
                            }
                        }
                        project.description?.toLowerCase()?.let {
                            if (it.contains(queryString)) {
                                filterList.add(project)
                            }
                        }
                    }
                    results.count = filterList.size
                    results.values = filterList
                } else {
                    results.count = projects.size
                    results.values = projects
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProjects = results?.values as MutableList<Project>
                notifyDataSetChanged()
            }
        }
    }
}