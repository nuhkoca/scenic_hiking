package com.kpit.scenichiking.util.permission

import android.app.Activity
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.DENIED
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.GRANTED
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.RATIONALE_ACCEPTED
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState.RATIONALE_DENIED
import io.reactivex.subjects.BehaviorSubject
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE
import pub.devrel.easypermissions.EasyPermissions
import java.lang.ref.WeakReference

@Suppress("TooManyFunctions")
abstract class AbstractPermissionDispatcher : PermissionDispatcher {

    private lateinit var host: WeakReference<Activity>

    private val permissionSubject = BehaviorSubject.create<PermissionState>()

    override fun attachToHost(host: Activity) {
        this.host = WeakReference(host)
    }

    protected abstract fun hasPermission(): Boolean

    override fun checkPermissionResult(): BehaviorSubject<PermissionState> = permissionSubject

    @Suppress("SameParameterValue")
    protected fun requestPermissions(
        message: String,
        reqCode: Int,
        permission: String
    ) {
        if (!hasPermission()) {
            host.get()?.let {
                EasyPermissions.requestPermissions(it, message, reqCode, permission)
            }
            return
        }
        permissionSubject.onNext(GRANTED)
    }

    override fun onActivityResult(requestCode: Int) {
        if (requestCode == DEFAULT_SETTINGS_REQ_CODE) {
            if (hasPermission()) {
                permissionSubject.onNext(GRANTED)
            } else {
                permissionSubject.onNext(DENIED)
                showDialog()
            }
        }
    }

    private fun showDialog() {
        host.get()?.let {
            AppSettingsDialog.Builder(it).build().show()
        }
    }

    override fun onRationaleDenied(requestCode: Int) {
        permissionSubject.onNext(RATIONALE_DENIED)
    }

    override fun onRationaleAccepted(requestCode: Int) {
        permissionSubject.onNext(RATIONALE_ACCEPTED)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        permissionSubject.onNext(DENIED)
        showDialog()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        permissionSubject.onNext(GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            host.get()
        )
    }

    override fun releaseHost() {
        host.clear()
    }

    enum class PermissionState(val type: Int) {

        DENIED(0), GRANTED(1), RATIONALE_DENIED(2), RATIONALE_ACCEPTED(3);

        companion object {
            private val states = values()

            fun of(value: Int) = states.firstOrNull { it.type == value } ?: DENIED
        }
    }
}
