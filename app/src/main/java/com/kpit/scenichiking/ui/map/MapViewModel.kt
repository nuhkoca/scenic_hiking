package com.kpit.scenichiking.ui.map

import androidx.lifecycle.LiveData
import com.github.ajalt.timberkt.w
import com.kpit.scenichiking.base.BaseViewModel
import com.kpit.scenichiking.util.executor.Executors
import com.kpit.scenichiking.util.permission.AbstractPermissionDispatcher.PermissionState
import com.kpit.scenichiking.util.permission.PermissionDispatcher
import com.kpit.scenichiking.vm.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val permissionDispatcher: PermissionDispatcher,
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors
) : BaseViewModel(compositeDisposable, executors) {

    private val _permissionResultLiveData = SingleLiveEvent<PermissionState>()
    val permissionResultLiveData: LiveData<PermissionState> get() = _permissionResultLiveData

    fun checkPermissionResult() {
        permissionDispatcher.checkPermissionResult()
            .subscribe(_permissionResultLiveData::setValue) {
                w { it?.localizedMessage.toString() }
            }
            .addTo(compositeDisposable)
    }
}
