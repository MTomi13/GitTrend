package tamas.marton.gittrend.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.home_list_item.view.*
import tamas.marton.gittrend.R
import tamas.marton.gittrend.inflate


class CardListAdapter : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    var cards: List<CardUIModel> = listOf()

    lateinit var cardClickListener: CardClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        val inflatedView = parent?.inflate(R.layout.home_list_item, false)
        return CardViewHolder(inflatedView!!)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        holder?.bind(cards[position], cardClickListener)
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CardUIModel, cardClickListener: CardClickListener) = with(itemView) {
            with(item) {
                name_text.text = name
                full_name_text.text = fullName
                updated_text.text = lastUpdated

                Glide.with(context)
                        .load(avatarUrl)
                        .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.github_placeholder)).into(avatar_image)

                setOnClickListener {
                    cardClickListener.onCardClick(item.details, itemView)
                }
            }
        }
    }
}