package com.kpit.scenichiking.di.component

import android.app.Application
import com.kpit.scenichiking.HikingApp
import com.kpit.scenichiking.di.module.ActivityBuilder
import com.kpit.scenichiking.di.module.AppModule
import com.kpit.scenichiking.di.module.ContextModule
import com.kpit.scenichiking.di.module.MapboxModule
import com.kpit.scenichiking.vm.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        ContextModule::class,
        ActivityBuilder::class,
        ViewModelModule::class,
        MapboxModule::class]
)
interface AppComponent : AndroidInjector<HikingApp> {

    override fun inject(instance: HikingApp?)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
