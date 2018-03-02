package tamas.marton.gittrend.home

import dagger.Module
import dagger.Provides


@Module
class HomeActivityModule {

    @Provides
    fun provideView(homeActivity: HomeActivity): HomeView {
        return homeActivity
    }

    @Provides
    fun provideInteractor(interactorImpl: HomeInteractorImpl): HomeInteractor {
        return interactorImpl
    }

    @Provides
    fun providePresenter(presenter: HomePresenterImpl): HomePresenter {
        return presenter
    }
}