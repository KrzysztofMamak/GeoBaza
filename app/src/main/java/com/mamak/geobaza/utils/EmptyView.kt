package com.mamak.geobaza.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mamak.geobaza.R
import kotlinx.android.synthetic.main.layout_empty_view.view.*

class EmptyView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null, true)
        addView(view)
    }

    fun showNoDataEmptyView(message: String = context.getString(R.string.ev_empty_project_list)) {
        iv_empty_view.setImageDrawable(context.getDrawable(R.drawable.ic_no_data))
        tv_empty_view.text = message
    }

    fun showNoInternetEmptyView(message: String = context.getString(R.string.ev_no_internet)) {
        iv_empty_view.setImageDrawable(context.getDrawable(R.drawable.ic_no_internet))
        tv_empty_view.text = message
    }

    fun showNoServiceEmptyView(message: String = context.getString(R.string.ev_no_service)) {
        iv_empty_view.setImageDrawable(context.getDrawable(R.drawable.ic_no_service))
        tv_empty_view.text = message
    }

    companion object {
        enum class EmptyViewType {
            NO_DATA, NO_INTERNET, NO_SERVICE
        }
    }
}