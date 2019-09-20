package com.kpit.scenichiking.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.kpit.scenichiking.util.config.Keys
import com.kpit.scenichiking.util.ext.filterApply
import com.kpit.scenichiking.util.ext.observeWith
import com.kpit.scenichiking.util.location.LocationEngineLiveData
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Failure
import com.kpit.scenichiking.util.location.LocationEngineLiveData.LocationState.Success
import com.kpit.scenichiking.util.location.NavigationLauncherHandler
import com.kpit.scenichiking.util.location.PermissionStateObserver
import com.kpit.scenichiking.util.map.MarkerUtil
import com.kpit.scenichiking.util.permission.PermissionDispatcher
import com.mapbox.mapboxsdk.Mapbox
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import pub.devrel.easypermissions.EasyPermissions.RationaleCallbacks
import javax.inject.Inject

abstract class PermissionActivity<VM : ViewModel> : BaseActivity<VM>(), RationaleCallbacks,
    PermissionCallbacks {

    @Inject
    protected lateinit var context: Context

    @Inject
    protected lateinit var permissionDispatcher: PermissionDispatcher

    @Inject
    protected lateinit var locationEngineLiveData: LocationEngineLiveData

    @Inject
    protected lateinit var markerUtil: MarkerUtil

    @Inject
    protected lateinit var navigationLauncherHandler: NavigationLauncherHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        Mapbox.getInstance(this, Keys.mapBoxKey())
        super.onCreate(savedInstanceState)
        PermissionStateObserver(this, permissionDispatcher)
    }

    protected fun observeLocationEngine() {
        locationEngineLiveData.observeWith(this) { locationState ->
            when (locationState) {
                is Success -> filterApply<LocationProvider> { onLocationSuccess(locationState.location) }
                is Failure -> filterApply<LocationProvider> { onLocationFailure(locationState.exception) }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionDispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onRationaleDenied(requestCode: Int) =
        permissionDispatcher.onRationaleDenied(requestCode)

    override fun onRationaleAccepted(requestCode: Int) =
        permissionDispatcher.onRationaleAccepted(requestCode)

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) =
        permissionDispatcher.onPermissionsDenied(requestCode, perms)

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) =
        permissionDispatcher.onPermissionsGranted(requestCode, perms)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        permissionDispatcher.onActivityResult(requestCode)
    }
}
