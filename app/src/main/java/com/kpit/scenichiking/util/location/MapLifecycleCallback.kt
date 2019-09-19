package com.kpit.scenichiking.util.location

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.kpit.scenichiking.ui.map.MapProvider

class MapLifecycleCallback : ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is MapProvider) {
            activity.getMapView()?.onCreate(savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity is MapProvider) {
            activity.getMapView()?.onStart()
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity is MapProvider) {
            activity.getMapView()?.onPause()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity is MapProvider) {
            activity.getMapView()?.onResume()
        }
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity is MapProvider) {
            activity.getMapView()?.onStop()
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity is MapProvider) {
            activity.getMapView()?.onDestroy()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (activity is MapProvider) {
            activity.getMapView()?.onSaveInstanceState(outState)
        }
    }
}
