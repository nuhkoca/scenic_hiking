package com.kpit.scenichiking.util.ext

import android.location.Location
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng

fun Location.toLatLng() = LatLng(latitude, longitude)

fun LatLng.toPoint(): Point = Point.fromLngLat(longitude, latitude)
