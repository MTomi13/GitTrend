package tamas.marton.gittrend.base


interface BaseView {

    fun showProgressBar()

    fun hideProgressBar()

    fun onError(message: String?)
}