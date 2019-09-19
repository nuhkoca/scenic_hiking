package com.kpit.scenichiking.vm

import androidx.annotation.MainThread

class ActionLiveEvent : SingleLiveEvent<Unit>() {
    @MainThread
    fun call() {
        value = Unit
    }
}
