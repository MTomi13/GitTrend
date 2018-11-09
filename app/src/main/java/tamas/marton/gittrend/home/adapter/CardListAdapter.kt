package tamas.marton.gittrend.home.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startPostponedEnterTransition
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.home_list_item.view.*
import tamas.marton.gittrend.R
import tamas.marton.gittrend.home.HomeActivity
import tamas.marton.gittrend.inflate


class CardListAdapter : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    private var cards: List<CardUIModel> = listOf()

    fun updateList(newCards: List<CardUIModel>) {
        val diffResult = DiffUtil.calculateDiff(CardDiffsUtilCallback(cards, newCards))
        cards = newCards
        diffResult.dispatchUpdatesTo(this)
    }

    lateinit var cardClickListener: CardClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflatedView = parent.inflate(R.layout.home_list_item, false)
        val cardViewHolder = CardViewHolder(inflatedView)
        inflatedView.setOnClickListener {
            cardClickListener.onCardClick(cards[cardViewHolder.adapterPosition].details, it, cardViewHolder.adapterPosition)
        }
        return cardViewHolder
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CardUIModel) = with(itemView) {
            with(item) {
                name_text.text = name
                full_name_text.text = fullName
                val updatedString = String.format(resources.getString(R.string.update_format), lastUpdated)
                updated_text.text = updatedString
                Glide.with(context)
                        .load(avatarUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                startPostponedEnterTransition(itemView.context as HomeActivity)
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                scheduleStartPostponedTransition(avatar_image, itemView.context as HomeActivity)
                                return false
                            }
                        })
                        .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.github_placeholder)).into(avatar_image)
            }
        }

        private fun scheduleStartPostponedTransition(imageView: ImageView, context: HomeActivity) {
            imageView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    imageView.viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition(context)
                    return true
                }
            })
        }
    }
}