package tamas.marton.gittrend.home.adapter

import tamas.marton.gittrend.details.DetailsUIModel


data class CardUIModel(

        var id: Int,

        var name: String,

        var fullName: String,

        var avatarUrl: String,

        var lastUpdated: String,

        var details: DetailsUIModel
)