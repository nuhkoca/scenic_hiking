package com.kpit.scenichiking.ui.map

import android.location.Location
import com.kpit.scenichiking.R
import com.kpit.scenichiking.base.LocationProvider
import com.kpit.scenichiking.base.PermissionActivity
import com.kpit.scenichiking.util.ext.enableLocationComponent
import com.kpit.scenichiking.util.ext.initWithDefault
import com.kpit.scenichiking.util.ext.observeWith
import com.kpit.scenichiking.util.ext.updateAndAnimate
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.GRANTED
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style.MAPBOX_STREETS
import kotlinx.android.synthetic.main.activity_map.mapView
import kotlinx.android.synthetic.main.activity_map.myLocationButton
import kotlinx.android.synthetic.main.activity_map.snackbar

class MapActivity : PermissionActivity<MapViewModel>(), OnMapReadyCallback, MapProvider,
    LocationProvider {

    private lateinit var mapboxMap: MapboxMap

    override val layoutId = R.layout.activity_map

    override fun getViewModelClass() = MapViewModel::class.java

    override fun getMapView(): MapView? = mapView

    override fun initView() {
        mapView.getMapAsync(this@MapActivity)
        myLocationButton.setOnClickListener {
            if (::mapboxMap.isInitialized) {
                mapboxMap.updateAndAnimate(mapboxMap.locationComponent.lastKnownLocation)
            }
        }
        viewModel.checkPermissionResult()
    }

    override fun observeViewModel() = with(viewModel) {
        permissionResultLiveData.observeWith(this@MapActivity) {
            when (it) {
                GRANTED -> observeLocationEngine()
                else -> { // no-op }
                }
            }
        }
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.initWithDefault()
        mapboxMap.setStyle(MAPBOX_STREETS) {
            mapboxMap.enableLocationComponent(context, it)
            observeViewModel()
        }
    }

    override fun onLocationSuccess(location: Location?) {
        mapboxMap.updateAndAnimate(location)
        snackbar.show(getString(R.string.location_auto_recentering_message))
    }

    override fun onLocationFailure(exception: Exception) {
        snackbar.show(exception.localizedMessage?.toString().toString())
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
