package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.base.BaseMapBoxFragment
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_project_map.*

class ProjectMapFragment(private val project: Project)
        : BaseMapBoxFragment(), OnMapReadyCallback, MapboxMap.OnMapClickListener {
    private val ID_ICON = "id-icon"

    //private lateinit var symbolManager: SymbolManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mv_project.onCreate(savedInstanceState)
        mv_project.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.MAPBOX_STREETS) {
            mapboxMap.addOnMapClickListener(this@ProjectMapFragment)
        }
    }

    override fun onMapClick(point: LatLng): Boolean {
        return true
    }

    private fun addSymbol(point: LatLng) {

    }

    private fun getStyleBuilder(styleUrl: String) {

    }

    private fun generateBitmap(drawableRes: Int) {

    }

    override fun onDestroyView() {
        mapboxMap.removeOnMapClickListener(this)
        super.onDestroyView()
    }
}