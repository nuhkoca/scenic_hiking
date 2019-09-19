package com.kpit.scenichiking.di.module

import com.kpit.scenichiking.di.scope.ActivityScope
import com.kpit.scenichiking.ui.map.MapActivity
import com.kpit.scenichiking.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideMapActivity(): MapActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideSplashActivity(): SplashActivity
}
