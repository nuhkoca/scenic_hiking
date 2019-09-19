package com.kpit.scenichiking.util.ext

import android.content.Context
import android.location.Location
import androidx.annotation.FloatRange
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode.TRACKING
import com.mapbox.mapboxsdk.location.modes.RenderMode.NORMAL
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

private const val DEFAULT_ZOOM_LEVEL = 15.0
private const val DEFAULT_ANIMATION_DURATION_IN_MS = 2000
private const val INVALID_MIN_ZOOM_VALUE = 0.0

fun MapboxMap.initWithDefault() {
    uiSettings.isCompassEnabled = true
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
    if (currentZoom.isZoomInInvalidRange) currentZoom = zoomLevel

    animateWithDefault(location, currentZoom, durationInMs)
}

private inline val Double.isZoomInInvalidRange: Boolean
    get() = this in INVALID_MIN_ZOOM_VALUE..DEFAULT_ZOOM_LEVEL
