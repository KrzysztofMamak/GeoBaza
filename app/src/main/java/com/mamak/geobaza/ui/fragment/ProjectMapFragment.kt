package com.mamak.geobaza.ui.fragment

import android.content.res.ColorStateList
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
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
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class ProjectMapFragment(private val project: Project) : Fragment() {
    private lateinit var myLocationNewOverlay: MyLocationNewOverlay
    private val markerList = mutableListOf<Marker>()
    private val polyline = Polyline()
    private var linesDrawn = false
    private var locationFollowed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setConfiguration()
        return layoutInflater.inflate(R.layout.fragment_project_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMap()
        drawPoints()
        setPolyline()
        setOnClicks()
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
            minZoomLevel = 7.0
        }
        setMapController()
        setMyLocationOverlay()
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
        myLocationNewOverlay = MyLocationNewOverlay(gpsMyLocationProvider, mv_project)
        mv_project.overlays.add(myLocationNewOverlay)
    }

    private fun setOnClicks() {
        iv_follow_location.setOnClickListener {
            if (!locationFollowed) {
                enableFollowLocation()
            } else {
                disableFollowLocation()
            }
        }
        iv_zoom_points.setOnClickListener {
            zoomToPoints()
        }
        iv_lines_switch.setOnClickListener {
            if (!linesDrawn) {
                drawPolyline()
            } else {
                removePolyline()
            }
        }
    }

    private fun drawPoints() {
        markerList.clear()
        project.apply {
            pointList.forEach {
                val marker = getMarker(
                    this.town,
                    "${this.street} - ${this.description}",
                    MappingManager.pointToGeoPoint(it)
                )
                mv_project.overlays.add(marker)
                markerList.add(marker)
            }
        }
        mv_project.invalidate()
        zoomToPoints()
    }

    private fun getMarker(pointTitle: String, pointDescription: String, geoPoint: GeoPoint): Marker {
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

    private fun setPolyline() {
        val points = project.pointList.map {
            MappingManager.pointToGeoPoint(it)
        }
        polyline.apply {
            setPoints(points)
            outlinePaint.color = resources.getColor(R.color.colorSecondaryDark, null)
        }
    }

    private fun drawPolyline() {
        mv_project.overlays.add(polyline)
        mv_project.invalidate()
        linesDrawn = true
        setButton(iv_lines_switch, linesDrawn)
    }

    private fun removePolyline() {
        mv_project.overlays.remove(polyline)
        mv_project.invalidate()
        linesDrawn = false
        setButton(iv_lines_switch, linesDrawn)
    }

    private fun setButton(imageView: ImageView, isActive: Boolean) {
        val foregroundColor: Int
        val backgroundColor: Int

        if (isActive) {
            foregroundColor = R.color.white
            backgroundColor = R.color.colorSecondaryDark
        } else {
            foregroundColor = R.color.colorSecondaryDark
            backgroundColor = R.color.white
        }

        imageView.apply {
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(context.getColor(foregroundColor))
            )
            backgroundTintList = ColorStateList.valueOf(context.getColor(backgroundColor))
        }
    }

    private fun zoomToPoints() {
        mv_project.zoomToBoundingBox(OsmManager.getBoundingBoxByPointList(
            project.pointList.toMutableList()),
            true
        )
    }

    private fun enableFollowLocation() {
        myLocationNewOverlay.enableFollowLocation()
        mv_project.invalidate()
        locationFollowed = true
        setButton(iv_follow_location, locationFollowed)
    }

    private fun disableFollowLocation() {
        myLocationNewOverlay.disableFollowLocation()
        mv_project.invalidate()
        locationFollowed = false
        setButton(iv_follow_location, locationFollowed)
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