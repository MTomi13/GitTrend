package tamas.marton.gittrend.home

import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.base.BaseView
import tamas.marton.gittrend.home.adapter.CardUIModel


interface HomeView : BaseView {

    fun setList(list: List<CardUIModel>)

    fun saveData(repositories: Repositories)

    fun showLoading()
}