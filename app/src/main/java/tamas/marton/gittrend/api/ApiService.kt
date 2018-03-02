package tamas.marton.gittrend.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import tamas.marton.gittrend.api.model.Repository


interface ApiService {

    @GET("/search/repositories")
    fun getRepositories(@Query("language") language: String, @Query("sort") sort: String): Observable<Repository>
}