package tamas.marton.gittrend.home.adapter

import tamas.marton.gittrend.details.DetailsUIModel


@Suppress("EqualsOrHashCode")
data class CardUIModel(

        var id: Int,

        var name: String,

        var fullName: String,

        var avatarUrl: String,

        var lastUpdated: String,

        var details: DetailsUIModel
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is CardUIModel)
            return false

        return id == other.id
    }
}