package tamas.marton.gittrend.home

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.db.RepositoriesEntity
import tamas.marton.gittrend.home.adapter.CardMapper
import javax.inject.Inject


class HomePresenterImpl @Inject constructor(private val homeInteractor: HomeInteractor, private val homeView: HomeView) : HomePresenter {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun getBestRepositories() {
        homeInteractor.getRepositories()

        compositeDisposable.add(homeInteractor.getRepositories()
                .map { result -> UIStateModel.success(result) }
                .onErrorReturn { exception -> UIStateModel.error(exception) }
                .startWith(UIStateModel.loading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
        Log.e("ee", "ee")

    }

    private fun onGetRepositoriesFailure(e: Throwable?) {
        homeView.hideProgressBar()
        Log.e("aa", "aa")
    }

    private fun onGetRepositoriesEmpty() {
        homeView.hideProgressBar()
        Log.e("bb", "bb")
    }


    override fun mapEntity(repositoriesEntity: RepositoriesEntity) {
        repositoriesEntity.let {
            val list = CardMapper().map(it.repositories)
            homeView.setList(list)
        }
    }

    override fun unSubscribe() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }
}