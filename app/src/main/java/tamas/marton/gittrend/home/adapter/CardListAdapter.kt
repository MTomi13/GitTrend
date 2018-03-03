package tamas.marton.gittrend.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item.view.*
import tamas.marton.gittrend.R
import tamas.marton.gittrend.inflate


class CardListAdapter : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    var cards: List<CardUIModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        val inflatedView = parent?.inflate(R.layout.list_item, false)
        return CardViewHolder(inflatedView!!)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        holder?.bind(cards[position])
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CardUIModel) = with(itemView) {
            with(item) {
                name_text.text = name
                full_name_text.text = fullName
                updated_text.text = lastUpdated

                Glide.with(context)
                        .load(avatarUrl)
                        .apply(RequestOptions.circleCropTransform()).into(avatar_image)

                setOnClickListener {
                    // TODO: Handle on click
                }
            }
        }
    }
}