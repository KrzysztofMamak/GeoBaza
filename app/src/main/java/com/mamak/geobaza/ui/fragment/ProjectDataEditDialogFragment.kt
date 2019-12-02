package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project

class ProjectDataEditDialogFragment(
    private val project: Project
) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_data_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}