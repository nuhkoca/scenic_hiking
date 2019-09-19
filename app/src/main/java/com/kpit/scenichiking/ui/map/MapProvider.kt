package com.kpit.scenichiking.ui.map

import com.mapbox.mapboxsdk.maps.MapView

interface MapProvider {
    fun getMapView(): MapView?
}
