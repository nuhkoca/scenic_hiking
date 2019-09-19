package com.kpit.scenichiking.util.location

import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Failure
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Success
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationEngineLiveData @Inject constructor(
    private val locationEngine: LocationEngine,
    private val request: LocationEngineRequest
) : LiveData<LocationState>() {

    private val callback = MapActivityLocationCallback()

    private fun initEngine() {
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
}
