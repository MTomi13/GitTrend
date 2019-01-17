package tamas.marton.gittrend.home

import kotlinx.coroutines.*
import tamas.marton.gittrend.api.EmptyResultException
import tamas.marton.gittrend.api.RepositoriesFetchingException
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.api.schedulers.DispatcherProvider
import tamas.marton.gittrend.db.RepositoriesEntity
import tamas.marton.gittrend.home.adapter.CardMapper
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class HomePresenterImpl @Inject constructor(private val homeInteractor: HomeInteractor,
                                            private val cardMapper: CardMapper,
                                            private val dispatcher: DispatcherProvider,
                                            private val homeView: HomeView,
                                            private val compositeJob: Job)
    : HomePresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatcher.mainThread() + compositeJob

    override fun getBestRepositories() {
        GlobalScope.launch(context = coroutineContext) {
            handleResponseState(UIStateModel.loading())
            try {
                getRepositories()
            } catch (exception: Exception) {
                onGetRepositoriesFailure(exception)
            }
        }
    }

    private suspend fun getRepositories() = coroutineScope {
        val result = withContext(dispatcher.backgroundThread()) {
            homeInteractor.getRepositories()
        }.await()

        withContext(dispatcher.backgroundThread()) {
            handleResponseState(if (result.isSuccessful) {
                UIStateModel.success(result.body()!!)
            } else {
                UIStateModel.error(RepositoriesFetchingException("Couldn't retrieve repositories list ${result.code()} -> ${result.message()}"))
            })
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