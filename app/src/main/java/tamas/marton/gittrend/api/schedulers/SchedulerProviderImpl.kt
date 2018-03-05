package tamas.marton.gittrend.api.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SchedulerProviderImpl : SchedulerProvider {

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    override fun backgroundThread(): Scheduler = Schedulers.io()
}