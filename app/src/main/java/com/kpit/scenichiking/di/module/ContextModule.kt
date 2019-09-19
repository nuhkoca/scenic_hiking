package com.kpit.scenichiking.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ContextModule {

    @Binds
    @Singleton
    internal abstract fun provideContext(application: Application): Context
}
