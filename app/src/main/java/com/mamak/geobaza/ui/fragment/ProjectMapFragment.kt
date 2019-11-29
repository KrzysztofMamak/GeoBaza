package com.mamak.geobaza.ui.fragment

import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.utils.manager.MappingManager
import com.mamak.geobaza.utils.manager.OsmManager
import kotlinx.android.synthetic.main.fragment_project_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.PathOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class ProjectMapFragment(private val project: Project) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setConfiguration()
        return layoutInflater.inflate(R.layout.fragment_project_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMap()
        drawPoints()
    }

    private fun setConfiguration() {
        Configuration.getInstance().apply {
            load(context, PreferenceManager.getDefaultSharedPreferences(context))
            userAgentValue = BuildConfig.APPLICATION_ID
        }
    }

    private fun setMap() {
        mv_project.apply {
            setUseDataConnection(true)
            setMultiTouchControls(true)
            setTileSource(TileSourceFactory.MAPNIK)
        }
        setMapController()
        setMyLocationOverlay()
        drawLinesThroughoutAllPoints()
    }

    private fun setMapController() {
        mv_project.apply {
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
            controller.setZoom(16.0)
        }
    }

    private fun setMyLocationOverlay() {
        val gpsMyLocationProvider = GpsMyLocationProvider(context)
        gpsMyLocationProvider.addLocationSource(LocationManager.GPS_PROVIDER)
        val locationOverlay = MyLocationNewOverlay(gpsMyLocationProvider, mv_project)
        mv_project.overlays.add(locationOverlay)
    }

    private fun drawPoints() {
        project.apply {
            pointList.forEach {
                val marker = createMarker(
                    this.town,
                    "${this.street} - ${this.description}",
                    MappingManager.pointToGeoPoint(it)
                )
                mv_project.overlays.add(marker)
            }
        }
    }

    private fun createMarker(pointTitle: String, pointDescription: String, geoPoint: GeoPoint): Marker {
        val marker = Marker(mv_project)
        marker.apply {
            icon = context?.getDrawable(R.drawable.ic_place)
            title = pointTitle
            subDescription = pointDescription
            position = geoPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        return marker
    }

    private fun zoomToCurrentLocation() {

    }

    private fun zoomToPoints() {
        mv_project.zoomToBoundingBox(OsmManager.createBoundingBoxFromPointList(
            project.pointList.toMutableList()),
            true
        )
    }

    private fun drawLinesThroughoutAllPoints() {
        val pathOverlay = PathOverlay(Color.BLACK)
        project.pointList.forEach {
            pathOverlay.addPoint(MappingManager.pointToGeoPoint(it))
        }
        mv_project.overlays.add(pathOverlay)
        zoomToPoints()
    }

    private fun removeAllLines() {

    }

    private fun zoomToAllOverlays() {
//        TODO zoom to all map overlays
    }

    override fun onResume() {
        super.onResume()
        mv_project.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_project.onPause()
    }
}