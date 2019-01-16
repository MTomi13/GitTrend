package tamas.marton.gittrend.api.schedulers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class DispatcherProviderImpl : DispatcherProvider {

    override fun mainThread(): CoroutineDispatcher = Dispatchers.Main

    override fun backgroundThread(): CoroutineDispatcher = Dispatchers.IO
}