package tamas.marton.gittrend.home

import tamas.marton.gittrend.base.BaseView
import tamas.marton.gittrend.home.adapter.CardUIModel


interface HomeView : BaseView {

    fun setList(list: List<CardUIModel>)
}