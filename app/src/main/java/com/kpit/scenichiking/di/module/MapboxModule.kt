package com.kpit.scenichiking.di.module

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineRequest.PRIORITY_HIGH_ACCURACY
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MapboxModule {

    private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 10000L
    private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5

    @Provides
    @Singleton
    @JvmStatic
    internal fun provideLocationEngine(context: Context) =
        LocationEngineProvider.getBestLocationEngine(context)

    @Provides
    @Singleton
    @JvmStatic
    internal fun provideLocationEngineRequest() =
        LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
            .setFastestInterval(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .build()

    @Provides
    @Singleton
    @JvmStatic
    internal fun provideSettingsClient(context: Context) =
        LocationServices.getSettingsClient(context)

    @Provides
    @Singleton
    @JvmStatic
    internal fun provideLocationSettingsRequest() =
        LocationSettingsRequest.Builder().addLocationRequest(
            LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        ).build()

}
