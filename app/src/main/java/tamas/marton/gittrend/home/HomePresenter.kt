package tamas.marton.gittrend.home

import tamas.marton.gittrend.base.BasePresenter


interface HomePresenter : BasePresenter {

    fun getBestRepositories()
}