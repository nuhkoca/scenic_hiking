package com.kpit.scenichiking.ui.map

import com.kpit.scenichiking.di.scope.ActivityScope
import com.kpit.scenichiking.ui.map.MapActivityModule.MapActivityStaticModule
import com.kpit.scenichiking.util.location.NavigationLauncherHandler
import com.kpit.scenichiking.util.location.NavigationLauncherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [MapActivityStaticModule::class])
abstract class MapActivityModule {

    @Binds
    @ActivityScope
    abstract fun provideNavigationLauncher(navigationLauncherHandler: NavigationLauncherHandler):
            NavigationLauncherProvider

    @Module
    object MapActivityStaticModule {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideNavigationLauncherHandler(activity: MapActivity) =
            NavigationLauncherHandler(activity)
    }
}
