package com.kpit.scenichiking.util.location

import android.content.Context
import android.content.IntentSender
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.common.ConnectionResult.RESOLUTION_REQUIRED
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Failure
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.GpsNotFound
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Success
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationEngineLiveData @Inject constructor(
    private val context: Context,
    private val locationEngine: LocationEngine,
    private val request: LocationEngineRequest
) : LiveData<LocationState>() {

    private val callback = MapActivityLocationCallback()

    private fun initEngine() {
        val settingsClient = LocationServices.getSettingsClient(context)
        val locationRequestBuilder = LocationSettingsRequest.Builder().addLocationRequest(
            LocationRequest().setPriority(PRIORITY_HIGH_ACCURACY)
        )

        val locationRequest = locationRequestBuilder?.build()

        settingsClient?.checkLocationSettings(locationRequest)?.run {
            addOnFailureListener {
                val statusCode = (it as ApiException).statusCode

                if (statusCode == RESOLUTION_REQUIRED) {
                    val resolvableException = it as? ResolvableApiException
                    value = try {
                        GpsNotFound(resolvableException)
                    } catch (exception: IntentSender.SendIntentException) {
                        Failure(exception)
                    }
                }
            }
        }

        locationEngine.requestLocationUpdates(request, callback, context.mainLooper)
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
        data class GpsNotFound(val exception: ResolvableApiException?) : LocationState()
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 1992
    }
}
