package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mamak.geobaza.R
import kotlinx.android.synthetic.main.fragment_project_sketch.*

class ProjectSketchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_project_sketch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        context?.let {
//            Glide.with(it)
//                .load(imageByteArray)
//                .asBitmap()
//                .into(iv_project_sketch)
//        }
    }
}