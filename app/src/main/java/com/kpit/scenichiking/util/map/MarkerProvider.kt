package com.kpit.scenichiking.util.map

import com.mapbox.mapboxsdk.maps.Style

interface MarkerProvider {
    fun addDestinationIconSymbolLayer(style: Style)
}
