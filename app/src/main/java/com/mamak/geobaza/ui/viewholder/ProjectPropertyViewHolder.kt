package com.mamak.geobaza.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_property_text_view.view.*

class ProjectPropertyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
    fun bindView(field: Pair<String, String?>) {
        setPropertyValue(field.second)
        setPropertyName(field.first)
    }

    private fun setPropertyValue(propertyValue: String?) {
        itemView.tv_property_value.text = propertyValue
    }
    private fun setPropertyName(propertyName: String) {
        itemView.tv_property_name.text = propertyName
    }
}