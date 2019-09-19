package com.kpit.scenichiking.util.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HikingExecutionThread @Inject constructor() : ExecutionThread {
    override val scheduler: Scheduler
        get() = Schedulers.io()
}
