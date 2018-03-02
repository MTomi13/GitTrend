package tamas.marton.gittrend

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import tamas.marton.gittrend.di.NetworkModule
import tamas.marton.gittrend.di.DaggerAppComponent
import javax.inject.Inject


class GitTrendApplication  : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        DaggerAppComponent
                .builder()
                .connectivityManager(connectivityManager)
                .networkModule(NetworkModule(connectivityManager))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }
}