package com.kpit.scenichiking.base

import androidx.lifecycle.ViewModel
import com.kpit.scenichiking.util.executor.Executors
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel constructor(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors
) : ViewModel()
