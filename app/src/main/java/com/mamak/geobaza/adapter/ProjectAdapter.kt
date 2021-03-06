package com.mamak.geobaza.adapter

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.communication.ProjectItemInterface
import com.mamak.geobaza.ui.viewholder.ProjectViewHolder
import com.mamak.geobaza.utils.manager.ProjectListManager
import kotlinx.android.synthetic.main.item_list_project_expandable.view.*

class ProjectAdapter(
    private val projectItemInterface: ProjectItemInterface
): RecyclerView.Adapter<ProjectViewHolder>(), Filterable {
    private var projects = mutableListOf<Project>()
    private var filteredProjects = mutableListOf<Project>()
    private var holderExpanded: ProjectViewHolder? = null
    private var location: Location? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_project_expandable, parent, false),
            projectItemInterface
        )
    }

    override fun getItemCount(): Int {
        return filteredProjects.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.apply {
            bind(filteredProjects[position])
            itemView.container_project.setOnClickListener {
                setExpanded()
                holderExpanded = if (holderExpanded?.adapterPosition == position) {
                    null
                } else {
                    holderExpanded?.setExpanded()
                    holder
                }
            }
        }
    }

    fun setProjects(projects: MutableList<Project>) {
        this.projects = projects
        this.filteredProjects = projects
        notifyDataSetChanged()
    }

    fun filterProjects() {
        filteredProjects = ProjectListManager.getRequiredProjects(projects)
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
                        run loop@{
                            listOf(project.area, project.town, project.street, project.description).forEach {
                                if (it.toLowerCase().contains(queryString)) {
                                    filterList.add(project)
                                    return@loop
                                }
                            }
                        }
//                        TODO Check if unnecessary
                        if (project.area.toLowerCase().contains(queryString)) {
                            filterList.add(project)
                        }
                        if (project.town.toLowerCase().contains(queryString)) {
                            filterList.add(project)
                        }
                        if (project.street.toLowerCase().contains(queryString)) {
                            filterList.add(project)
                        }
                        if (project.description.toLowerCase().contains(queryString)) {
                            filterList.add(project)
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
                @Suppress("UNCHECKED_CAST")
                filteredProjects = results?.values as MutableList<Project>
                notifyDataSetChanged()
            }
        }
    }
}