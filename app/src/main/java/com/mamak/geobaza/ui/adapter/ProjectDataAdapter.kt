package com.mamak.geobaza.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.viewholder.ProjectPropertyViewHolder

class ProjectDataAdapter : RecyclerView.Adapter<ProjectPropertyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectPropertyViewHolder {
        return ProjectPropertyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_property, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ProjectPropertyViewHolder, position: Int) {
        holder.bindView()
    }
}