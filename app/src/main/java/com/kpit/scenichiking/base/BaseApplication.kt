package com.kpit.scenichiking.base

import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.Timber.DebugTree
import com.kpit.scenichiking.util.location.MapLifecycleCallback
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseApplication : DaggerApplication(), HasAndroidInjector, LifecycleObserver {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        setupTimber()
    }

    @OnLifecycleEvent(ON_RESUME)
    fun appIsInForeground() {
        registerActivityLifecycleCallbacks(MapLifecycleCallback())
    }

    @OnLifecycleEvent(ON_STOP)
    fun appIsInBackground() {
        unregisterActivityLifecycleCallbacks(MapLifecycleCallback())
    }

    abstract override fun applicationInjector(): AndroidInjector<out DaggerApplication>

    override fun androidInjector() = dispatchingAndroidInjector

    private fun setupTimber() {
        Timber.uprootAll()
        Timber.plant(DebugTree())
    }
}
