package tamas.marton.gittrend.home

import io.reactivex.disposables.CompositeDisposable
import tamas.marton.gittrend.api.EmptyResultException
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.api.schedulers.SchedulerProvider
import tamas.marton.gittrend.db.RepositoriesEntity
import tamas.marton.gittrend.home.adapter.CardMapper
import javax.inject.Inject


class HomePresenterImpl @Inject constructor(private val homeInteractor: HomeInteractor,
                                            private val cardMapper: CardMapper,
                                            private val scheduler: SchedulerProvider,
                                            private val homeView: HomeView)
    : HomePresenter {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun getBestRepositories() {
        compositeDisposable.add(homeInteractor.getRepositories()
                .map { result -> UIStateModel.success(result) }
                .onErrorReturn { exception -> UIStateModel.error(exception) }
                .startWith(UIStateModel.loading())
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
                .subscribe({ uiState ->
                    when {
                        uiState.isLoading() -> homeView.showLoading()
                        uiState.isError() -> onGetRepositoriesFailure(uiState.getError())
                        uiState.isSuccess() -> onGetRepositoriesSuccess(uiState.getRepositories())
                        uiState.isEmpty() -> onGetRepositoriesEmpty()
                        else -> IllegalArgumentException("Invalid Response")
                    }
                }))
    }

    private fun onGetRepositoriesSuccess(repositories: Repositories) {
        homeView.saveData(repositories)
    }

    private fun onGetRepositoriesFailure(error: Throwable) {
        handleErrors()
        homeView.onError(error.message)
    }

    private fun onGetRepositoriesEmpty() {
        handleErrors()
        homeView.onError(EmptyResultException().message)
    }

    private fun handleErrors() {
        homeView.hideProgressBar()
        homeView.hideSwipeRefreshLoader()
    }

    override fun mapEntity(repositoriesEntity: RepositoriesEntity) {
        repositoriesEntity.let {
            val list = cardMapper.map(it.repositories)
            homeView.setList(list)
        }
    }

    override fun unSubscribe() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }
}