package com.kpit.scenichiking.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.w
import com.kpit.scenichiking.base.BaseViewModel
import com.kpit.scenichiking.data.Resource
import com.kpit.scenichiking.util.executor.Executors
import com.kpit.scenichiking.util.location.NavigationRouteProvider
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.GRANTED
import com.kpit.scenichiking.util.permission.PermissionDispatcher
import com.kpit.scenichiking.vm.SingleLiveEvent
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Point
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val permissionDispatcher: PermissionDispatcher,
    private val navigationRouteProvider: NavigationRouteProvider,
    private val compositeDisposable: CompositeDisposable,
    executors: Executors
) : BaseViewModel(compositeDisposable, executors) {

    private val _permissionResultLiveData = SingleLiveEvent<PermissionState>()
    val permissionResultLiveData: LiveData<PermissionState> get() = _permissionResultLiveData

    private val _routeLiveData = MutableLiveData<Resource<DirectionsResponse>>()
    val routeLiveData: LiveData<Resource<DirectionsResponse>> get() = _routeLiveData

    fun checkPermissionResult() {
        permissionDispatcher.checkPermissionResult()
            .subscribe({
                if (PermissionState.of(it.ordinal) == GRANTED) {
                    _permissionResultLiveData.value = it
                }
            }, {
                w { it?.localizedMessage.toString() }
            })
            .addTo(compositeDisposable)
    }

    fun getRoute(origin: Point, destination: Point) {
        navigationRouteProvider.getNavigationRoute(origin, destination)
            .getRoute(object : Callback<DirectionsResponse> {
                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                    _routeLiveData.value = Resource.error(t)
                }

                override fun onResponse(
                    call: Call<DirectionsResponse>,
                    response: Response<DirectionsResponse>
                ) {
                    response.body()?.let { routeData ->
                        if (routeData.routes().size < MIN_ROUTE_COUNT) {
                            _routeLiveData.value = Resource.error(Throwable(routeData.message()))
                            return
                        }

                        _routeLiveData.value = Resource.success(routeData)
                    }
                }
            })
    }

    companion object {
        private const val MIN_ROUTE_COUNT = 1
    }
}
