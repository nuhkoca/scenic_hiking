package com.kpit.scenichiking.di.module

import com.kpit.scenichiking.di.module.AppModule.StaticAppModule
import com.kpit.scenichiking.util.executor.ComputationThread
import com.kpit.scenichiking.util.executor.ExecutionThread
import com.kpit.scenichiking.util.executor.HikingComputationThread
import com.kpit.scenichiking.util.executor.HikingExecutionThread
import com.kpit.scenichiking.util.executor.HikingPostExecutionThread
import com.kpit.scenichiking.util.executor.PostExecutionThread
import com.kpit.scenichiking.util.permission.LocationPermissionDispatcher
import com.kpit.scenichiking.util.permission.PermissionDispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module(includes = [StaticAppModule::class])
abstract class AppModule {

    @Binds
    @Singleton
    internal abstract fun provideLocationPermissionDispatcher(
        locationPermissionDispatcher: LocationPermissionDispatcher
    ): PermissionDispatcher

    @Binds
    @Singleton
    internal abstract fun provideExecutionThread(
        executionThread: HikingExecutionThread
    ): ExecutionThread

    @Binds
    @Singleton
    internal abstract fun providePostExecutionThread(
        postExecutionThread: HikingPostExecutionThread
    ): PostExecutionThread

    @Binds
    @Singleton
    internal abstract fun provideComputationThread(
        computationThread: HikingComputationThread
    ): ComputationThread

    @Module
    object StaticAppModule {

        @Provides
        @JvmStatic
        internal fun provideCompositeDisposable() = CompositeDisposable()
    }
}
