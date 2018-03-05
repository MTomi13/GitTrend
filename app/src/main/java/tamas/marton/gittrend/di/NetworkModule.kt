package tamas.marton.gittrend.di

import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tamas.marton.gittrend.api.ApiService
import tamas.marton.gittrend.api.NoConnectionInterceptor
import tamas.marton.gittrend.api.schedulers.SchedulerProvider
import tamas.marton.gittrend.api.schedulers.SchedulerProviderImpl
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule(private val connectivityManager: ConnectivityManager) {

    private val TIMEOUT: Long = 5000
    private val BASE_URL: String = "https://api.github.com"

    @Provides
    @Singleton
    fun noConnectionInterceptor(): NoConnectionInterceptor {
        return NoConnectionInterceptor(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(noConnectionInterceptor: NoConnectionInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(noConnectionInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun getCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}