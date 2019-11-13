package com.mamak.geobaza.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.fragment_project_map.*

open class BaseMapBoxFragment : Fragment() {
    protected lateinit var mapboxMap: MapboxMap

    override fun onStart() {
        super.onStart()
        mv_project.onStart()
    }

    override fun onResume() {
        super.onResume()
        mv_project.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_project.onPause()
    }

    override fun onStop() {
        super.onStop()
        mv_project.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mv_project.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mv_project.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mv_project.onSaveInstanceState(outState)
    }
}