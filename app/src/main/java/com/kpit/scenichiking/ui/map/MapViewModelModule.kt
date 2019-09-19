package com.kpit.scenichiking.ui.map

import androidx.lifecycle.ViewModel
import com.kpit.scenichiking.vm.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MapViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun provideMapViewModel(mapViewModel: MapViewModel): ViewModel
}
