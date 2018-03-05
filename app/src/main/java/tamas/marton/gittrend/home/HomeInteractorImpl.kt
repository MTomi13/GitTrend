package tamas.marton.gittrend.home

import io.reactivex.Observable
import tamas.marton.gittrend.api.ApiService
import tamas.marton.gittrend.api.model.Repositories
import javax.inject.Inject


class HomeInteractorImpl @Inject constructor(private val apiService: ApiService) : HomeInteractor {

    override fun getRepositories(): Observable<Repositories> =
            apiService.getRepositories("language:java", "stars", "desc")
}