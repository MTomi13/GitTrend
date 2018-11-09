package tamas.marton.gittrend.di

import android.net.ConnectivityManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import tamas.marton.gittrend.GitTrendApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (NetworkModule::class), (RoomModule::class), (ActivityBuilder::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun connectivityManager(connectivityManager: ConnectivityManager): Builder

        fun networkModule(networkModule: NetworkModule): Builder
        fun roomModule(roomModule: RoomModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: GitTrendApplication)
}