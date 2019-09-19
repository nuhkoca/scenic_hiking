package com.kpit.scenichiking.util.executor

import io.reactivex.Scheduler

interface ComputationThread {
    val scheduler: Scheduler
}
