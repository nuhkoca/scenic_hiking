package com.kpit.scenichiking.util.location

import android.content.Context
import com.kpit.scenichiking.util.config.Keys
import com.mapbox.geojson.Point
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import javax.inject.Inject

class NavigationRouteBuilder @Inject constructor(private val context: Context) : INavigationRoute {
    override fun getNavigationRoute(origin: Point, destination: Point): NavigationRoute =
        NavigationRoute.builder(context)
            .accessToken(Keys.mapBoxKey())
            .origin(origin)
            .destination(destination)
            .build()
}
