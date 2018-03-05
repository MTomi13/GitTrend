package tamas.marton.gittrend.base

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*
import tamas.marton.gittrend.R


abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView {

    private val progressBar: ConstraintLayout by lazy { findViewById<ConstraintLayout>(R.id.progress_bar) }

    abstract var presenter: P

    protected abstract fun getContentView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onError(message: String?) {
        var errorMessage = message
        if (errorMessage == null) {
            errorMessage = getString(R.string.default_error)
        }
        errorMessage = String.format(resources.getString(R.string.error_message), errorMessage)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }
}