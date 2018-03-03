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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.onGetRepositoriesSuccess(repositories = it) }) { this.onGetRepositoriesFailure(e = it) })
    }

    private fun onGetRepositoriesSuccess(repositories: Repositories) {
        homeView.saveData(repositories)
        Log.e("ee", "ee")

    }

    private fun onGetRepositoriesFailure(e: Throwable?) {
        Log.e("aa", "aa")
    }

    override fun mapEntity(repositoriesEntity: RepositoriesEntity?) {
        repositoriesEntity?.let {
            if (it.repositories.items.isNotEmpty()) {
                val list = CardMapper().map(it.repositories)
                homeView.setList(list)
            }
        }
    }

    override fun unSubscribe() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }
}