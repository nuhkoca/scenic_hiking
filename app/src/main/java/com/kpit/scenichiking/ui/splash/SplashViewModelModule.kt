package com.kpit.scenichiking.ui.splash

import androidx.lifecycle.ViewModel
import com.kpit.scenichiking.vm.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun provideSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}
