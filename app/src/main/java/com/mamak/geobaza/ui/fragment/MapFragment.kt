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
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.MapViewModel
import com.mamak.geobaza.utils.manager.MappingManager
import com.mamak.geobaza.utils.manager.OsmManager
import com.mamak.geobaza.utils.manager.ThemeManager
import dagger.android.support.AndroidSupportInjection
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
import javax.inject.Inject

class MapFragment(private val projectNumber: Int = -1) : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var mapViewModel: MapViewModel

    private var projects = mutableListOf<Project>()
    private var currentProject: Project? = null
    private lateinit var myLocationNewOverlay: MyLocationNewOverlay
    private val markerList = mutableListOf<Marker>()
    private val polyline = Polyline()
    private var polylineVisibility = false
    private var followLocation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
        getProjects()
    }

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

    private fun initViewModel() {
        mapViewModel = viewModelFactory.create(MapViewModel::class.java)
    }

    private fun getProjects() {
        projects = mapViewModel.getProjectsFromDatabase().toMutableList()
        projects.forEach {
            if (it.number == projectNumber) {
                currentProject = it
            }
        }
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
            if (!followLocation) {
                enableFollowLocation()
            } else {
                disableFollowLocation()
            }
        }
        iv_zoom_points.setOnClickListener {
            zoomToPoints()
        }
        iv_lines_switch.setOnClickListener {
            if (!polylineVisibility) {
                drawPolyline()
            } else {
                removePolyline()
            }
        }
    }

    private fun drawPoints() {
        markerList.clear()
        for (project in projects) {
            val isCurrent = project.number == projectNumber
            project.apply {
                pointList.forEach {
                    val marker = getMarker(
                        this.town,
                        "${this.street} - ${this.description}",
                        MappingManager.pointToGeoPoint(it),
                        isCurrent
                    )
                    mv_project.overlays.add(marker)
                    markerList.add(marker)
                }
            }
        }
        mv_project.invalidate()
        zoomToPoints()
    }

    private fun getMarker(
        pointTitle: String,
        pointDescription: String,
        geoPoint: GeoPoint,
        isCurrent: Boolean
    ): Marker {
        val marker = Marker(mv_project)
        val color = if (isCurrent) {
            ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryDark)
        } else {
            R.color.light_gray
        }
        marker.apply {
            icon = context?.getDrawable(R.drawable.ic_place)?.apply {
                setTint(activity.getColor(
                    color
                ))
            }
            title = pointTitle
            subDescription = pointDescription
            position = geoPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        return marker
    }

    private fun setPolyline() {
        val points = currentProject?.pointList?.map {
            MappingManager.pointToGeoPoint(it)
        }
        points?.let {
            polyline.apply {
                setPoints(it)
                outlinePaint.color = ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryLight)
            }
        }
    }

    private fun zoomToPoints() {
        currentProject?.let {
            mv_project.zoomToBoundingBox(
                OsmManager.getBoundingBoxByPointList(
                    it.pointList.toMutableList()),
                true
            )
            disableFollowLocation()
        }
    }

    private fun enableFollowLocation() {
        myLocationNewOverlay.enableFollowLocation()
        mv_project.invalidate()
        followLocation = true
        setButton(iv_follow_location, followLocation)
    }

    private fun disableFollowLocation() {
        myLocationNewOverlay.disableFollowLocation()
        mv_project.invalidate()
        followLocation = false
        setButton(iv_follow_location, followLocation)
    }

    private fun drawPolyline() {
        mv_project.overlays.add(polyline)
        mv_project.invalidate()
        polylineVisibility = true
        setButton(iv_lines_switch, polylineVisibility)
    }

    private fun removePolyline() {
        mv_project.overlays.remove(polyline)
        mv_project.invalidate()
        polylineVisibility = false
        setButton(iv_lines_switch, polylineVisibility)
    }

    private fun setButton(imageView: ImageView, isActive: Boolean) {
        val foregroundColor: Int =
            if (isActive) {
                R.color.white
            } else {
                ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryDark)
            }
        val backgroundColor: Int =
            if (isActive) {
                ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryDark)
            } else {
                R.color.white
            }

        imageView.apply {
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(context.getColor(foregroundColor))
            )
            backgroundTintList = ColorStateList.valueOf(context.getColor(backgroundColor))
        }
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