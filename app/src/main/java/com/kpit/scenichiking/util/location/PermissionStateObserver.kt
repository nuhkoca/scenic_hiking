package com.kpit.scenichiking.util.location

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kpit.scenichiking.util.ext.addObserver
import com.kpit.scenichiking.util.ext.removeObserver
import com.kpit.scenichiking.util.permission.PermissionDispatcher

class PermissionStateObserver constructor(
    private val activity: ComponentActivity,
    private val permissionDispatcher: PermissionDispatcher
) : LifecycleObserver {

    init {
        activity.addObserver(this)
    }

    @OnLifecycleEvent(ON_CREATE)
    fun onHostAttach() {
        permissionDispatcher.apply {
            attachToHost(activity)
            initRequest()
        }
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun onHostRelease() {
        permissionDispatcher.releaseHost()
        activity.removeObserver(this)
    }
}
