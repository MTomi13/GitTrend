package tamas.marton.gittrend.api.schedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.rx2.asCoroutineDispatcher


class DispatcherProviderImpl : DispatcherProvider {

    override fun mainThread(): CoroutineDispatcher = AndroidSchedulers.mainThread().asCoroutineDispatcher()

    override fun backgroundThread(): CoroutineDispatcher = Schedulers.io().asCoroutineDispatcher()

    override fun dbThread(): CoroutineDispatcher = Schedulers.single().asCoroutineDispatcher()
}