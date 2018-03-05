package tamas.marton.gittrend.home

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import tamas.marton.gittrend.base.ViewModelFactory
import tamas.marton.gittrend.db.RepositoriesDao
import tamas.marton.gittrend.home.adapter.CardListAdapter
import tamas.marton.gittrend.home.adapter.CardMapper
import javax.inject.Singleton


@Module
class HomeActivityModule {

    @Provides
    fun provideView(homeActivity: HomeActivity): HomeView {
        return homeActivity
    }

    @Provides
    fun provideCardMapper(): CardMapper {
        return CardMapper()
    }

    @Provides
    fun provideInteractor(interactorImpl: HomeInteractorImpl): HomeInteractor {
        return interactorImpl
    }

    @Provides
    fun providePresenter(presenter: HomePresenterImpl): HomePresenter {
        return presenter
    }

    @Provides
    fun provideAlbumListAdapter(): CardListAdapter {
        return CardListAdapter()
    }

    @Provides
    fun provideLinearLayoutManager(homeActivity: HomeActivity): LinearLayoutManager {
        return LinearLayoutManager(homeActivity)
    }

    @Singleton
    @Provides
    fun provideHomeViewModel(repositoriesDao: RepositoriesDao): HomeViewModel {
        return HomeViewModel(repositoriesDao)
    }

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }
}