package tamas.marton.gittrend.home

import tamas.marton.gittrend.BasePresenter


interface HomePresenter : BasePresenter {

    fun getBestRepositories()
}