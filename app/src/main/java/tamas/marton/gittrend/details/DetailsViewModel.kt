package tamas.marton.gittrend.details

import android.arch.lifecycle.ViewModel
import tamas.marton.gittrend.db.RepositoriesDao
import javax.inject.Inject


class DetailsViewModel @Inject constructor(private val repositoriesDao: RepositoriesDao) : ViewModel() {
}