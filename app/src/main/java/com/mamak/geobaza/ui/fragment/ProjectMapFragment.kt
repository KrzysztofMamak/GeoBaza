package com.mamak.geobaza.ui.fragment

import android.Manifest
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.buchandersenn.android_permission_manager.PermissionManager
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.utils.constans.AppConstans
import kotlinx.android.synthetic.main.fragment_project_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class ProjectMapFragment(private val project: Project): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setConfiguration()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMap()
    }

    private fun setMap() {
        mv_project.onResume()
        mv_project.setUseDataConnection(true)
        mv_project.setMultiTouchControls(true)
        mv_project.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mv_project.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)

        val gpsMyLocationProvider = GpsMyLocationProvider(context)
        gpsMyLocationProvider.addLocationSource(LocationManager.GPS_PROVIDER)
        val locationOverlay = MyLocationNewOverlay(gpsMyLocationProvider, mv_project)

        mv_project.overlays.add(locationOverlay)
        mv_project.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        mv_project.controller.setZoom(16.0)

        drawPointsOnMap()
    }

    private fun drawPointsOnMap() {
        val items = mutableListOf<OverlayItem>()
        project.apply {
            pointList.forEach {
                items.add(OverlayItem(this.area, this.street, GeoPoint(it.x, it.y)))
            }
        }

        val overlay = ItemizedOverlayWithFocus<OverlayItem>(items,
                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
            override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                return true
            }

            override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                return false
            }
        }, context)

        overlay.setFocusItemsOnTap(true)
        mv_project.overlays.add(overlay)
    }

    private fun setConfiguration() {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context?.packageName
    }
}