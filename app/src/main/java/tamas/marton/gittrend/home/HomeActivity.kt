package tamas.marton.gittrend.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.SharedElementCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val CLICKED_POSITION = "clicked_position"

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
    private var clickedPosition: Int? = 0

    override fun getContentView(): Int = R.layout.activity_home

    override var presenter: HomePresenter
        get() = homePresenter
        set(value) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            state = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE)
            clickedPosition = savedInstanceState.getInt(CLICKED_POSITION)
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
        prepareTransitions()
        postponeEnterTransition()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAYOUT_MANAGER_STATE, linearLayoutManager.onSaveInstanceState())
        outState.putInt(CLICKED_POSITION, clickedPosition!!)
    }

    override fun saveData(repositories: Repositories) {
        ioThread {
            homeViewModel?.addReposToDB(RepositoriesEntity(repositories))
        }
    }

    override fun setList(list: List<CardUIModel>) {
        runOnUiThread {
            adapter.updateList(list)
            hideProgressBar()
            hideSwipeRefreshLoader()
            if (state != null) {
                linearLayoutManager.onRestoreInstanceState(state)
            }
        }
    }

    override fun hideSwipeRefreshLoader() {
        swipe_refresh_layout.isRefreshing = false
    }

    override fun showLoading() {
        if (linearLayoutManager.itemCount == 0) {
            showProgressBar()
        }
    }

    override fun onCardClick(detailsUIModel: DetailsUIModel, view: View, position: Int) {
        clickedPosition = position
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DETAILS_INTENT, detailsUIModel)
        val sharedImage = view.findViewById<ImageView>(R.id.avatar_image)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedImage, getString(R.string.shared_element_key))
        startActivity(intent, options.toBundle())
    }

    private fun prepareTransitions() {
        setExitSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>?, sharedElements: MutableMap<String, View>?) {
                        // Locate the ViewHolder for the clicked position.
                        val selectedViewHolder = recyclerView.findViewHolderForAdapterPosition(clickedPosition!!)
                        if (selectedViewHolder?.itemView == null) {
                            return
                        }
                        // Map the first shared element name to the child ImageView.
                        sharedElements!![names!![0]] = selectedViewHolder.itemView.findViewById(R.id.avatar_image)
                    }
                })
    }
}