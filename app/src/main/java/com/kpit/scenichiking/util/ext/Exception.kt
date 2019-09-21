package com.kpit.scenichiking.util.ext

import android.app.Activity
import com.google.android.gms.common.api.ResolvableApiException
import com.kpit.scenichiking.util.location.LocationEngineLiveData.Companion.REQUEST_CHECK_SETTINGS

fun ResolvableApiException?.checkGpsStatus(
    activity: Activity,
    reqCode: Int = REQUEST_CHECK_SETTINGS
) {
    this?.startResolutionForResult(activity, reqCode)
}
