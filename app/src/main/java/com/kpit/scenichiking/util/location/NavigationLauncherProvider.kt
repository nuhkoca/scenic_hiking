package com.kpit.scenichiking.util.location

import com.mapbox.api.directions.v5.models.DirectionsRoute

interface NavigationLauncherProvider {
    fun startNavigation(directionsRoute: DirectionsRoute)
}
