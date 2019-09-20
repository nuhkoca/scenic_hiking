package com.kpit.scenichiking.util.map

import android.content.Context
import android.graphics.BitmapFactory
import com.kpit.scenichiking.R
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import javax.inject.Inject

class MarkerUtil @Inject constructor(private val context: Context) : MarkerProvider {

    override fun addDestinationIconSymbolLayer(style: Style) {
        style.addImage(
            DESTINATION_ICON_ID,
            BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_marker_icon_default)
        )
        val geoJsonSource = GeoJsonSource(DESTINATION_SOURCE_ID)
        style.addSource(geoJsonSource)
        val destinationSymbolLayer = SymbolLayer(DESTINATION_SYMBOL_LAYER_ID, DESTINATION_SOURCE_ID)
        destinationSymbolLayer.withProperties(
            iconImage(DESTINATION_ICON_ID),
            iconAllowOverlap(true),
            iconIgnorePlacement(true)
        )
        style.addLayer(destinationSymbolLayer)
    }

    companion object {
        private const val DESTINATION_ICON_ID = "destination-icon-id"
        const val DESTINATION_SOURCE_ID = "destination-source-id"
        private const val DESTINATION_SYMBOL_LAYER_ID = "destination-symbol-layer-id"
    }
}
