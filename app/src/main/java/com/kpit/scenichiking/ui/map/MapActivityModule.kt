package com.kpit.scenichiking.ui.map

import com.kpit.scenichiking.di.scope.ActivityScope
import com.kpit.scenichiking.util.location.NavigationLauncherHandler
import dagger.Module
import dagger.Provides

@Module
object MapActivityModule {

    @Provides
    @ActivityScope
    @JvmStatic
    fun provideNavigationLauncher(activity: MapActivity) = NavigationLauncherHandler(activity)
}
