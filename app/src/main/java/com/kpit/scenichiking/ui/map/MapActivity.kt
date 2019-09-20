package com.kpit.scenichiking.ui.map

import android.location.Location
import com.kpit.scenichiking.R
import com.kpit.scenichiking.base.LocationProvider
import com.kpit.scenichiking.base.PermissionActivity
import com.kpit.scenichiking.data.Resource.Status.ERROR
import com.kpit.scenichiking.data.Resource.Status.LOADING
import com.kpit.scenichiking.data.Resource.Status.SUCCESS
import com.kpit.scenichiking.util.ext.easeCameraWithBounds
import com.kpit.scenichiking.util.ext.enableLocationComponent
import com.kpit.scenichiking.util.ext.initWithDefault
import com.kpit.scenichiking.util.ext.observeWith
import com.kpit.scenichiking.util.ext.safeLet
import com.kpit.scenichiking.util.ext.show
import com.kpit.scenichiking.util.ext.slideDown
import com.kpit.scenichiking.util.ext.toPoint
import com.kpit.scenichiking.util.ext.updateAndAnimate
import com.kpit.scenichiking.util.location.NavigationLauncherProvider
import com.kpit.scenichiking.util.map.MarkerUtil.Companion.DESTINATION_SOURCE_ID
import com.kpit.scenichiking.util.provider.DimenProvider
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style.MAPBOX_STREETS
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import kotlinx.android.synthetic.main.activity_map.mapView
import kotlinx.android.synthetic.main.activity_map.myLocationButton
import kotlinx.android.synthetic.main.activity_map.snackbar
import kotlinx.android.synthetic.main.activity_map.startNavigation
import javax.inject.Inject

class MapActivity : PermissionActivity<MapViewModel>(), OnMapReadyCallback, MapProvider,
    LocationProvider, MapboxMap.OnMapLongClickListener {

    @Inject
    lateinit var dimenProvider: DimenProvider

    @Inject
    lateinit var navigationLauncherProvider: NavigationLauncherProvider

    private lateinit var mapboxMap: MapboxMap
    private lateinit var directionsRoute: DirectionsRoute
    private var navigationMapRoute: NavigationMapRoute? = null
    private lateinit var latLngBounds: LatLngBounds
    private var isInNavigationMode: Boolean = false

    override val layoutId = R.layout.activity_map

    override fun getViewModelClass() = MapViewModel::class.java

    override fun getMapView(): MapView? = mapView

    override fun initView() {
        mapView.getMapAsync(this@MapActivity)
        myLocationButton.setOnClickListener {
            if (::mapboxMap.isInitialized) {
                if (isInNavigationMode.not()) {
                    mapboxMap.updateAndAnimate(mapboxMap.locationComponent.lastKnownLocation)
                } else {
                    easeCamera(latLngBounds)
                }
            }
        }
        startNavigation.setOnClickListener {
            navigationLauncherProvider.startNavigation(directionsRoute)
        }
        viewModel.checkPermissionResult()
    }

    override fun observeViewModel() = with(viewModel) {
        permissionResultLiveData.observeWith(this@MapActivity) {
            mapboxMap.enableLocationComponent(context, mapboxMap.style!!)
            observeLocationEngine()
        }
        routeLiveData.observeWith(this@MapActivity) {
            when (it.status) {
                SUCCESS -> {
                    it.data?.routes()?.get(0)?.let { route ->
                        directionsRoute = route
                        drawRoute(directionsRoute)
                    }
                }
                ERROR -> snackbar.show(it.throwable?.localizedMessage.toString())
                LOADING -> { // no-op
                }
            }
        }
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        this.mapboxMap.apply {
            initWithDefault()
            setStyle(MAPBOX_STREETS) {
                markerUtil.addDestinationIconSymbolLayer(it)
                addOnMapLongClickListener(this@MapActivity)
                enableLocationComponent(context, it)
                observeViewModel()
            }
        }
    }

    override fun onMapLongClick(point: LatLng): Boolean {
        isInNavigationMode = true
        with(mapboxMap.locationComponent.lastKnownLocation) {
            safeLet(this?.longitude, this?.latitude) { long, lat ->
                val destinationPoint = point.toPoint()
                val originPoint = Point.fromLngLat(long, lat)
                val geoSource = mapboxMap.style?.getSourceAs<GeoJsonSource>(DESTINATION_SOURCE_ID)
                geoSource?.setGeoJson(Feature.fromGeometry(destinationPoint))
                viewModel.getRoute(originPoint, destinationPoint)
                latLngBounds = LatLngBounds.Builder()
                    .include(point)
                    .include(LatLng(lat, long))
                    .build()
                easeCamera(latLngBounds)
            }
        }
        return true
    }

    private fun easeCamera(latLngBounds: LatLngBounds) {
        mapboxMap.easeCameraWithBounds(
            latLngBounds,
            dimenProvider.getMapPaddingLeftInPx(),
            dimenProvider.getMapPaddingTopInPx(),
            dimenProvider.getMapPaddingRightInPx(),
            dimenProvider.getMapPaddingBottomInPx()
        )
    }

    private fun drawRoute(directionsRoute: DirectionsRoute) {
        navigationMapRoute?.updateRouteArrowVisibilityTo(false) ?: run {
            navigationMapRoute =
                NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute)
        }
        navigationMapRoute?.addRoute(directionsRoute)
        startNavigation.slideDown { startNavigation.show() }
    }

    override fun onLocationSuccess(location: Location?) {
        if (isInNavigationMode.not()) {
            mapboxMap.updateAndAnimate(location)
        } else {
            easeCamera(latLngBounds)
        }
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
