package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mamak.geobaza.data.model.Project
import kotlinx.android.synthetic.main.fragment_project_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem

class ProjectMapFragment(private val project: Project): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context?.packageName
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMap()
        setMapController()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setMap() {
        mv_project.setUseDataConnection(true)
        mv_project.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mv_project.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        mv_project.setMultiTouchControls(true)
        drawPointsOnMap()
    }

    private fun setMapController() {
        val mapController = mv_project.controller
        mapController.setZoom(14.0)
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
}