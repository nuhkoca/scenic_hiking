package com.kpit.scenichiking.util.ext

import android.location.Location
import com.mapbox.mapboxsdk.geometry.LatLng

fun Location.toLatLng() = LatLng(latitude, longitude)
