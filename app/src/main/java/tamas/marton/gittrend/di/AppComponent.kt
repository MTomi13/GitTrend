package tamas.marton.gittrend.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import tamas.marton.gittrend.GitTrendApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (NetworkModule::class), (ActivityBuilder::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appModule(networkModule: NetworkModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: GitTrendApplication)
}