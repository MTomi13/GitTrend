package tamas.marton.gittrend.api.schedulers

import io.reactivex.Scheduler


interface SchedulerProvider {

    fun mainThread(): Scheduler

    fun backgroundThread(): Scheduler
}