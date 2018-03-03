package tamas.marton.gittrend.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import tamas.marton.gittrend.db.RepositoriesDao
import tamas.marton.gittrend.db.RepositoriesEntity
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val repositoriesDao: RepositoriesDao) : ViewModel() {

    fun getReposFromDB(): LiveData<RepositoriesEntity>? {
        return repositoriesDao.getRepositories()
    }

    fun addReposToDB(repositoriesEntity: RepositoriesEntity) {
        repositoriesDao.addRepositories(repositoriesEntity)
    }
}