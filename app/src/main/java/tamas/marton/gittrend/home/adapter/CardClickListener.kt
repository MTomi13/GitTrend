package tamas.marton.gittrend.home.adapter

import android.view.View
import tamas.marton.gittrend.details.DetailsUIModel


interface CardClickListener {

    fun onCardClick(detailsUIModel: DetailsUIModel, view: View, position: Int)
}