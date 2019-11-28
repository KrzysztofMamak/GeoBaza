package com.mamak.geobaza.ui.fragment

import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.utils.manager.CoordinatesManager
import kotlinx.android.synthetic.main.fragment_project_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class ProjectMapFragment(private val project: Project) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setConfiguration()
        return layoutInflater.inflate(R.layout.fragment_project_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMap()
        drawPointsOnMap()
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
        setMapOverlay()
    }

    private fun setMapController() {
        mv_project.apply {
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
            controller.setZoom(16.0)
        }
    }

    private fun setMapOverlay() {
        val gpsMyLocationProvider = GpsMyLocationProvider(context)
        gpsMyLocationProvider.addLocationSource(LocationManager.GPS_PROVIDER)
        val locationOverlay = MyLocationNewOverlay(gpsMyLocationProvider, mv_project)

        mv_project.overlays.add(locationOverlay)
    }

    private fun drawPointsOnMap() {
        val items = mutableListOf<OverlayItem>()
        project.apply {
            pointList.forEach {
                val geoCoordinates = CoordinatesManager.tr2000WGS(doubleArrayOf(it.x, it.y)).asList()
                items.add(OverlayItem(this.area, this.street, GeoPoint(geoCoordinates[0], geoCoordinates[1])))
            }
        }

        val overlay = ItemizedOverlayWithFocus<OverlayItem>(
            items,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            }, context
        )

        overlay.setFocusItemsOnTap(true)
        mv_project.overlays.add(overlay)
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