package com.kpit.scenichiking.util.location

import com.mapbox.api.directions.v5.models.DirectionsRoute

interface INavigationLauncher {
    fun startNavigation(directionsRoute: DirectionsRoute)
}
