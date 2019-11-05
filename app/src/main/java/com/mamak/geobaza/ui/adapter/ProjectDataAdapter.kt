package com.mamak.geobaza.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.viewholder.ProjectPropertyViewHolder

class ProjectDataAdapter : RecyclerView.Adapter<ProjectPropertyViewHolder>() {
    private var properties = mutableListOf<Pair<String, String?>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectPropertyViewHolder {
        return ProjectPropertyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_property, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: ProjectPropertyViewHolder, position: Int) {
        holder.bindView(properties[position])
    }

    fun setProperties(fields: MutableList<Pair<String, String?>>) {
        this.properties = fields
    }
}