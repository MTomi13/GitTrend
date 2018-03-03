package tamas.marton.gittrend.home

import dagger.Module
import dagger.Provides
import android.support.v7.widget.LinearLayoutManager
import tamas.marton.gittrend.home.adapter.CardListAdapter


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

    @Provides
    fun provideAlbumListAdapter(): CardListAdapter {
        return CardListAdapter()
    }

    @Provides
    fun provideLinearLayoutManager(homeActivity: HomeActivity): LinearLayoutManager {
        return LinearLayoutManager(homeActivity)
    }
}