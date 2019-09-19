package com.kpit.scenichiking.util.ext

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver

inline fun <reified A : ComponentActivity> A.addObserver(lifecycleObserver: LifecycleObserver) {
    lifecycle.addObserver(lifecycleObserver)
}

inline fun <reified A : ComponentActivity> A.removeObserver(lifecycleObserver: LifecycleObserver) {
    lifecycle.removeObserver(lifecycleObserver)
}

inline fun <reified T : Any> Activity.filterApply(block: T.() -> Unit) {
    if (this is T) {
        block()
    }
}

inline fun <reified T : Any> Activity.launchActivity(
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Any> Activity.newIntent(): Intent =
    Intent(this, T::class.java)
