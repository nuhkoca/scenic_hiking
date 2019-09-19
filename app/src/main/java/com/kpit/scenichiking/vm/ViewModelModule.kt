package com.kpit.scenichiking.vm

import androidx.lifecycle.ViewModelProvider
import com.kpit.scenichiking.ui.map.MapViewModelModule
import com.kpit.scenichiking.ui.splash.SplashViewModelModule
import dagger.Binds
import dagger.Module

@Module(includes = [MapViewModelModule::class, SplashViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsHikingViewModelFactory(
        hikingViewModelFactory: HikingViewModelFactory
    ): ViewModelProvider.Factory
}
