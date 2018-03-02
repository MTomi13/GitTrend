package tamas.marton.gittrend.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import tamas.marton.gittrend.R
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homePresenter.getBestRepositories()
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.unSubscribe()
    }
}
