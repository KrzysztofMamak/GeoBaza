package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_project_map.*

class ProjectMapFragment(private val project: Project?)
        : Fragment(), OnMapReadyCallback {
    private lateinit var mapboxMap: MapboxMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mv_project_map.onCreate(savedInstanceState)
        mv_project_map.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.MAPBOX_STREETS)
    }

    override fun onStart() {
        super.onStart()
        mv_project_map.onStart()
    }

    override fun onResume() {
        super.onResume()
        mv_project_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_project_map.onPause()
    }

    override fun onStop() {
        super.onStop()
        mv_project_map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mv_project_map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mv_project_map.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mv_project_map.onSaveInstanceState(outState)
    }
}