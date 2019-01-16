package tamas.marton.gittrend.home

import kotlinx.coroutines.Deferred
import retrofit2.Response
import tamas.marton.gittrend.api.model.Repositories


interface HomeInteractor {

    fun getRepositories(): Deferred<Response<Repositories>>
}