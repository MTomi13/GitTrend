package tamas.marton.gittrend.home

import io.reactivex.Observable
import tamas.marton.gittrend.api.model.Repositories


interface HomeInteractor {

    fun getRepositories(): Observable<Repositories>
}