package tamas.marton.gittrend.details

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*
import tamas.marton.gittrend.DETAILS_INTENT
import tamas.marton.gittrend.R


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val details = intent.getParcelableExtra<DetailsUIModel>(DETAILS_INTENT)

        setupViews(details)
        setupToolbar(details)
    }

    private fun setupToolbar(details: DetailsUIModel) {
        setSupportActionBar(details_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        collapsing_toolbar.title = details.name
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
        collapsing_toolbar.setExpandedTitleColor(Color.WHITE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews(details: DetailsUIModel) {
        Glide.with(this)
                .load(details.avatarUrl)
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.github_placeholder)).into(avatar_details_image)

        setupLanguage(details.language)
        description_text.text = details.description
        val createdString = String.format(resources.getString(R.string.create_format), details.created)
        created_text.text = createdString
        val updatedString = String.format(resources.getString(R.string.update_format), details.lastUpdated)
        last_updated_text.text = updatedString
        watcher_count.text = details.watchersCount.toString()
        star_count.text = details.starsCount.toString()
        fork_count.text = details.forksCount.toString()
    }

    private fun setupLanguage(language: String) {
        if (language.isEmpty()) {
            language_text.visibility = View.GONE
        } else {
            language_text.text = language
        }
    }
}