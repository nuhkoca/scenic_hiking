package com.kpit.scenichiking

import com.kpit.scenichiking.base.BaseApplication
import com.kpit.scenichiking.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class HikingApp : BaseApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this).also { appComponent ->
            appComponent.inject(this)
        }
    }
}
