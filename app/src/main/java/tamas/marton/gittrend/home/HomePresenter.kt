package tamas.marton.gittrend.home

import tamas.marton.gittrend.base.BasePresenter
import tamas.marton.gittrend.db.RepositoriesEntity


interface HomePresenter : BasePresenter {

    fun mapEntity(repositoriesEntity: RepositoriesEntity)

    fun getBestRepositories()
}