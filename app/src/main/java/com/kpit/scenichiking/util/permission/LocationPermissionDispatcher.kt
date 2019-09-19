package com.kpit.scenichiking.util.permission

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import com.kpit.scenichiking.R
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class LocationPermissionDispatcher @Inject constructor(private val context: Context) :
    AbstractPermissionDispatcher() {

    override fun hasPermission() = EasyPermissions.hasPermissions(context, ACCESS_FINE_LOCATION)

    @AfterPermissionGranted(REQUEST_CODE_LOCATION_PERMISSION)
    override fun initRequest() {
        requestPermissions(
            context.getString(R.string.location_req_message),
            REQUEST_CODE_LOCATION_PERMISSION,
            ACCESS_FINE_LOCATION
        )
    }

    companion object {
        const val REQUEST_CODE_LOCATION_PERMISSION = 100
    }
}
