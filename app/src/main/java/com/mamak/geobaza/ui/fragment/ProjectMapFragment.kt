package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_project_map.*

class ProjectMapFragment(private val project: Project?)
        : Fragment(), OnMapReadyCallback, MapboxMap.OnMapClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mv_project_map.onCreate(savedInstanceState)
        mv_project_map.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {

    }

    override fun onMapClick(point: LatLng): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}