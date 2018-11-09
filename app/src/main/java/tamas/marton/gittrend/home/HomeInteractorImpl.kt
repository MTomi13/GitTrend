package tamas.marton.gittrend.home

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.rx2.openSubscription
import tamas.marton.gittrend.api.ApiService
import tamas.marton.gittrend.api.model.Repositories
import javax.inject.Inject

private const val LANGUAGE = "language:java"
private const val SORT = "stars"
private const val ORDER = "desc"

class HomeInteractorImpl @Inject constructor(private val apiService: ApiService) : HomeInteractor {

    @ObsoleteCoroutinesApi
    override fun getRepositories(): ReceiveChannel<Repositories> =
            apiService.getRepositories(LANGUAGE, SORT, ORDER).openSubscription()
}