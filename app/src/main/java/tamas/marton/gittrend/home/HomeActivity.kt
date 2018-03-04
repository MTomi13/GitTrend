package tamas.marton.gittrend.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import tamas.marton.gittrend.DETAILS_INTENT
import tamas.marton.gittrend.R
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.base.BaseActivity
import tamas.marton.gittrend.base.ViewModelFactory
import tamas.marton.gittrend.db.RepositoriesEntity
import tamas.marton.gittrend.details.DetailsActivity
import tamas.marton.gittrend.details.DetailsUIModel
import tamas.marton.gittrend.home.adapter.CardClickListener
import tamas.marton.gittrend.home.adapter.CardListAdapter
import tamas.marton.gittrend.home.adapter.CardUIModel
import tamas.marton.gittrend.ioThread
import javax.inject.Inject


class HomeActivity : BaseActivity<HomePresenter>(), HomeView, CardClickListener {

    private val LAYOUT_MANAGER_STATE = "layout_manager_state"

    @Inject
    lateinit var homePresenter: HomePresenter
    @Inject
    lateinit var adapter: CardListAdapter
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var homeViewModel: HomeViewModel? = null
    private var state: Parcelable? = null

    override fun getContentView(): Int = R.layout.activity_home

    override var presenter: HomePresenter
        get() = homePresenter
        set(value) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            state = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE)
        }
        adapter.cardClickListener = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        swipe_refresh_layout.setOnRefreshListener {
            presenter.getBestRepositories()
            state = null
        }

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        homeViewModel!!.getReposFromDB()?.observe(this, Observer<RepositoriesEntity> { entity ->
            if (entity != null) {
                presenter.mapEntity(entity)
            } else {
                presenter.getBestRepositories()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAYOUT_MANAGER_STATE, linearLayoutManager.onSaveInstanceState())
    }

    override fun saveData(repositories: Repositories) {
        ioThread {
            homeViewModel?.addReposToDB(RepositoriesEntity(repositories))
        }
    }

    override fun setList(list: List<CardUIModel>) {
        runOnUiThread {
            adapter.cards = list
            adapter.notifyDataSetChanged()
            hideProgressBar()
            swipe_refresh_layout.isRefreshing = false
            if (state != null) {
                linearLayoutManager.onRestoreInstanceState(state)
            }
        }
    }

    override fun showLoading() {
        if (linearLayoutManager.itemCount == 0) {
            showProgressBar()
        }
    }

    override fun onCardClick(detailsUIModel: DetailsUIModel, view: View) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DETAILS_INTENT, detailsUIModel)
        val sharedImage = view.findViewById<ImageView>(R.id.avatar_image)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedImage, getString(R.string.shared_element_key))
        startActivity(intent, options.toBundle())
    }
}