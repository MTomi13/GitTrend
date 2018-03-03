package tamas.marton.gittrend.details

import android.os.Bundle
import dagger.android.AndroidInjection
import tamas.marton.gittrend.base.BaseActivity
import javax.inject.Inject


class DetailsActivity : BaseActivity<DetailsPresenter>(), DetailsView {

    @Inject
    lateinit var detailsPresenter: DetailsPresenter

    override var presenter: DetailsPresenter
        get() = detailsPresenter
        set(value) {}

    override fun getContentView(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}