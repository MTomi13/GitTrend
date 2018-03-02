package tamas.marton.gittrend.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import tamas.marton.gittrend.api.model.Repositories


interface ApiService {

    @GET("/search/repositories")
    fun getRepositories(@Query("q") language: String, @Query("sort") sort: String, @Query("order") order: String): Observable<Repositories>
}