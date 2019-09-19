package com.kpit.scenichiking.util.location

import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Failure
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Success
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineRequest.PRIORITY_HIGH_ACCURACY
import com.mapbox.android.core.location.LocationEngineResult
import javax.inject.Inject

class LocationEngineLiveData @Inject constructor(private val context: Context) :
    LiveData<LocationState>() {

    private lateinit var locationEngine: LocationEngine
    private val callback = MapActivityLocationCallback()

    private fun initEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(context)
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
            .setFastestInterval(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .build()

        locationEngine.requestLocationUpdates(request, callback, Looper.getMainLooper())
        locationEngine.getLastLocation(callback)
    }

    override fun onActive() {
        super.onActive()
        initEngine()
    }

    override fun onInactive() {
        super.onInactive()
        locationEngine.removeLocationUpdates(callback)
    }

    inner class MapActivityLocationCallback : LocationEngineCallback<LocationEngineResult> {
        override fun onSuccess(result: LocationEngineResult?) {
            val location = result?.lastLocation
            value = Success(location)
        }

        override fun onFailure(exception: Exception) {
            value = Failure(exception)
        }
    }

    sealed class LocationState {
        data class Success(val location: Location?) : LocationState()
        data class Failure(val exception: Exception) : LocationState()
    }

    companion object {
        private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
        private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
    }
}
