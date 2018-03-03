package tamas.marton.gittrend.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import tamas.marton.gittrend.R
import tamas.marton.gittrend.base.BaseActivity
import tamas.marton.gittrend.home.adapter.CardListAdapter
import tamas.marton.gittrend.home.adapter.CardUIModel
import javax.inject.Inject

class HomeActivity : BaseActivity<HomePresenter>(), HomeView {

    override var presenter: HomePresenter
        get() = homePresenter
        set(value) {}

    @Inject
    lateinit var homePresenter: HomePresenter
    @Inject
    lateinit var adapter: CardListAdapter
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        presenter.getBestRepositories()

    }

    override fun setList(list: List<CardUIModel>) {
        runOnUiThread {
            adapter.cards = list
            adapter.notifyDataSetChanged()
        }
    }
}