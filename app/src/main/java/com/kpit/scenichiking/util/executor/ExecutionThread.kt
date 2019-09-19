package com.kpit.scenichiking.util.executor

import io.reactivex.Scheduler

interface ExecutionThread {
    val scheduler: Scheduler
}
