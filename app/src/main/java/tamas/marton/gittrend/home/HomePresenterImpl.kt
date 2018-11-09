package tamas.marton.gittrend.home

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tamas.marton.gittrend.api.EmptyResultException
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.api.schedulers.DispatcherProvider
import tamas.marton.gittrend.db.RepositoriesEntity
import tamas.marton.gittrend.home.adapter.CardMapper
import javax.inject.Inject


class HomePresenterImpl @Inject constructor(private val homeInteractor: HomeInteractor,
                                            private val cardMapper: CardMapper,
                                            private val dispatcher: DispatcherProvider,
                                            private val homeView: HomeView)
    : HomePresenter {

    @Inject
    lateinit var compositeJob: Job

    override fun getBestRepositories() {
        GlobalScope.launch(dispatcher.mainThread() + compositeJob) {
            handleResponseState(UIStateModel.loading())
            try {
                getRepositories()
            } catch (exception: Exception) {
                onGetRepositoriesFailure(exception)
            }
        }
    }

    private suspend fun getRepositories() {
        val result = withContext(dispatcher.backgroundThread()) {
            homeInteractor.getRepositories()
        }.map { repository ->
            UIStateModel.success(repository)
        }

        withContext(dispatcher.dbThread()) {
            handleResponseState(result.receive())
        }
    }

    private fun handleResponseState(uiState: UIStateModel) {
        when {
            uiState.isLoading() -> homeView.showLoading()
            uiState.isError() -> onGetRepositoriesFailure(uiState.getError())
            uiState.isSuccess() -> onGetRepositoriesSuccess(uiState.getRepositories())
            uiState.isEmpty() -> onGetRepositoriesEmpty()
            else -> IllegalArgumentException("Invalid Response")
        }
    }

    private fun onGetRepositoriesSuccess(repositories: Repositories) {
        homeView.saveData(repositories)
    }

    private fun onGetRepositoriesFailure(error: Exception) {
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
        if (!compositeJob.isCancelled) {
            compositeJob.cancel()
        }
    }
}