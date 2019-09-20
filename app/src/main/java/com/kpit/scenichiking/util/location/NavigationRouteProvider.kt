package com.kpit.scenichiking.util.location

import com.mapbox.geojson.Point
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute

interface NavigationRouteProvider {
    fun getNavigationRoute(origin: Point, destination: Point): NavigationRoute
}
