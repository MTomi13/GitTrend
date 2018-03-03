package tamas.marton.gittrend.base

import android.support.v7.app.AppCompatActivity


abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView {

   abstract var presenter : P

    override fun showProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }
}