package com.mamak.geobaza.ui.`interface`

import android.content.Context
import com.mamak.geobaza.utils.manager.LocationManager

//TODO Inject Interface
class ProjectListItemInterfaceImpl(val context: Context)
        : ProjectListItemInterface {
    override fun openGoogleMaps(x: Double, y: Double) {
        LocationManager.navigateByGeoCoordinates(context, x, y)
    }

    override fun openProjectDetails(number: Int?) {
        //TODO - start ProjectDetailsActivity
    }

    class Builder(val context: Context) {
        fun build(): ProjectListItemInterfaceImpl {
            return ProjectListItemInterfaceImpl(context)
        }
    }
}