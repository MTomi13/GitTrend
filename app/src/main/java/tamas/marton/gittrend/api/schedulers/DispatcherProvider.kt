package tamas.marton.gittrend.api.schedulers

import kotlinx.coroutines.CoroutineDispatcher


interface DispatcherProvider {

    fun mainThread(): CoroutineDispatcher

    fun backgroundThread(): CoroutineDispatcher
}