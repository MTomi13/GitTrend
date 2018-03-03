package tamas.marton.gittrend.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import tamas.marton.gittrend.db.RepositoriesDao
import tamas.marton.gittrend.home.HomeViewModel
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val repositoriesDao: RepositoriesDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repositoriesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}