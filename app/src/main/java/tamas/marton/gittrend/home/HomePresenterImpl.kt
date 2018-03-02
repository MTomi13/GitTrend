package tamas.marton.gittrend.home

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tamas.marton.gittrend.api.model.Repositories
import javax.inject.Inject


class HomePresenterImpl @Inject constructor(val homeInteractor: HomeInteractor, val homeView: HomeView) : HomePresenter {

    var subscription: Disposable? = null

    override fun getBestRepositories() {
        homeInteractor.getRepositories()

        subscription = homeInteractor.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetRepositoriesSuccess, this::onGetRepositoriesFailure)
    }

    private fun onGetRepositoriesSuccess(repositories: Repositories) {
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