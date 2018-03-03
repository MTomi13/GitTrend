package tamas.marton.gittrend.home

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.home.adapter.CardMapper
import javax.inject.Inject


class HomePresenterImpl @Inject constructor(val homeInteractor: HomeInteractor, val homeView: HomeView) : HomePresenter {


    // use compositeDisposeble
    var subscription: Disposable? = null

    override fun getBestRepositories() {
        homeInteractor.getRepositories()

        subscription = homeInteractor.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.onGetRepositoriesSuccess(repositories = it) }) { this.onGetRepositoriesFailure(e = it) }
    }

    private fun onGetRepositoriesSuccess(repositories: Repositories) {
        if (repositories.items.isNotEmpty()) {
            val list = CardMapper().map(repositories)
            homeView.setList(list)
        }

        Log.e("ee", "ee")

    }

    private fun onGetRepositoriesFailure(e: Throwable?) {
        Log.e("aa", "aa")
    }

    override fun unSubscribe() {
        if (subscription != null && !subscription!!.isDisposed) {
            subscription!!.dispose()
        }
    }
}