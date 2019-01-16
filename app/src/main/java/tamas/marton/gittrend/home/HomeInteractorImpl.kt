package tamas.marton.gittrend.home

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ObsoleteCoroutinesApi
import retrofit2.Response
import tamas.marton.gittrend.api.ApiService
import tamas.marton.gittrend.api.model.Repositories
import javax.inject.Inject

private const val LANGUAGE = "language:java"
private const val SORT = "stars"
private const val ORDER = "desc"

class HomeInteractorImpl @Inject constructor(private val apiService: ApiService) : HomeInteractor {

    @ObsoleteCoroutinesApi
    override fun getRepositories(): Deferred<Response<Repositories>> =
            apiService.getRepositories(LANGUAGE, SORT, ORDER)
}