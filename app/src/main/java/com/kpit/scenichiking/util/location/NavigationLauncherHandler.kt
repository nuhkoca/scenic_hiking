package com.kpit.scenichiking.util.location

import android.app.Activity
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions

class NavigationLauncherHandler constructor(private val activity: Activity) : INavigationLauncher {

    private fun getNavigationLauncherOptions(directionsRoute: DirectionsRoute): NavigationLauncherOptions =
        NavigationLauncherOptions.builder()
            .directionsRoute(directionsRoute)
            .shouldSimulateRoute(true)
            .build()

    override fun startNavigation(directionsRoute: DirectionsRoute) {
        NavigationLauncher.startNavigation(activity, getNavigationLauncherOptions(directionsRoute))
    }
}
