package tamas.marton.gittrend.details

import dagger.Module
import dagger.Provides
import tamas.marton.gittrend.db.RepositoriesDao
import javax.inject.Singleton


@Module
class DetailsActivityModule {

    @Provides
    fun provideDetailsView(detailsActivity: DetailsActivity): DetailsView {
        return detailsActivity
    }

    @Provides
    fun provideDetailsPresenter(presenter: DetailsPresenterImpl): DetailsPresenter {
        return presenter
    }

    @Singleton
    @Provides
    fun provideDetailsViewModel(repositoriesDao: RepositoriesDao): DetailsViewModel {
        return DetailsViewModel(repositoriesDao)
    }
}