package com.kpit.scenichiking.util.ext

import android.content.Context
import android.location.Location
import androidx.annotation.FloatRange
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode.TRACKING
import com.mapbox.mapboxsdk.location.modes.RenderMode.NORMAL
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

private const val DEFAULT_ZOOM_LEVEL = 15.0
private const val DEFAULT_ANIMATION_DURATION_IN_MS = 2000
private const val DEFAULT_PADDING = 0

fun MapboxMap.initWithDefault() {
    uiSettings.isCompassEnabled = false
    uiSettings.isZoomGesturesEnabled = true
}

fun LocationComponent.initWithDefault() {
    isLocationComponentEnabled = true
    cameraMode = TRACKING
    renderMode = NORMAL
}

fun MapboxMap.animateWithDefault(
    location: Location?,
    @FloatRange zoomLevel: Double = DEFAULT_ZOOM_LEVEL,
    durationInMs: Int = DEFAULT_ANIMATION_DURATION_IN_MS
) {
    val position = CameraPosition.Builder()
        .target(location?.toLatLng())
        .zoom(zoomLevel)
        .build()

    animateCamera(CameraUpdateFactory.newCameraPosition(position), durationInMs)
}

fun MapboxMap.enableLocationComponent(context: Context, style: Style) {
    if (PermissionsManager.areLocationPermissionsGranted(context)) {
        val locationComponent = locationComponent
        val options = LocationComponentActivationOptions.builder(context, style)
            .useDefaultLocationEngine(false)
            .build()
        locationComponent.activateLocationComponent(options)
        locationComponent.initWithDefault()
    }
}

fun MapboxMap.updateAndAnimate(
    location: Location?,
    @FloatRange zoomLevel: Double = DEFAULT_ZOOM_LEVEL,
    durationInMs: Int = DEFAULT_ANIMATION_DURATION_IN_MS
) {
    locationComponent.forceLocationUpdate(location)

    var currentZoom = cameraPosition.zoom

    if (currentZoom <= zoomLevel) currentZoom = zoomLevel

    animateWithDefault(location, currentZoom, durationInMs)
}

fun MapboxMap.easeCameraWithBounds(
    latLngBounds: LatLngBounds,
    paddingLeft: Int = DEFAULT_PADDING,
    paddingTop: Int = DEFAULT_PADDING,
    paddingRight: Int = DEFAULT_PADDING,
    paddingBottom: Int = DEFAULT_PADDING,
    durationInMs: Int = DEFAULT_ANIMATION_DURATION_IN_MS
) {
    easeCamera(
        CameraUpdateFactory.newLatLngBounds(
            latLngBounds,
            paddingLeft,
            paddingTop,
            paddingRight,
            paddingBottom
        ), durationInMs
    )
}
