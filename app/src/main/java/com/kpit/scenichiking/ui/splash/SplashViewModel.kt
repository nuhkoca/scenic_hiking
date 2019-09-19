package com.kpit.scenichiking.ui.splash

import androidx.lifecycle.LiveData
import com.github.ajalt.timberkt.w
import com.kpit.scenichiking.base.BaseViewModel
import com.kpit.scenichiking.util.executor.Executors
import com.kpit.scenichiking.vm.ActionLiveEvent
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors
) : BaseViewModel(compositeDisposable, executors) {

    private val _timerEndedLiveData = ActionLiveEvent()
    val timerEndedLiveData: LiveData<Unit> get() = _timerEndedLiveData

    init {
        startTimer()
    }

    private fun startTimer() {
        Completable.timer(DEFAULT_DELAY, MILLISECONDS, executors.worker)
            .observeOn(executors.ui)
            .subscribe({
                _timerEndedLiveData.call()
            }, {
                w { "An error occurred." }
            })
            .addTo(compositeDisposable)
    }

    companion object {
        private const val DEFAULT_DELAY = 2500L
    }
}
