package com.kpit.scenichiking.base

import android.location.Location

interface LocationProvider {
    fun onLocationSuccess(location: Location?)

    fun onLocationFailure(exception: Exception)
}
