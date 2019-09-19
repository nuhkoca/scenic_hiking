package com.kpit.scenichiking.util.permission

import android.app.Activity
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState
import io.reactivex.subjects.BehaviorSubject

interface PermissionDispatcher : PermissionResultProvider {
    fun attachToHost(host: Activity)

    fun initRequest()

    fun checkPermissionResult(): BehaviorSubject<PermissionState>

    fun onActivityResult(requestCode: Int)

    fun releaseHost()
}
