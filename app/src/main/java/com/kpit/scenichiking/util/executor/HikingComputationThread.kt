package com.kpit.scenichiking.util.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HikingComputationThread @Inject constructor() : ComputationThread {
    override val scheduler: Scheduler
        get() = Schedulers.computation()
}
