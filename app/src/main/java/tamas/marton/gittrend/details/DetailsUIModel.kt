package tamas.marton.gittrend.details

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsUIModel(

        var name: String,

        var description: String,

        var language: String,

        var type: String,

        var avatarUrl: String,

        var lastUpdated: String,

        var created: String,

        var size: Int,

        var starsCount: Int,

        var watchersCount: Int,

        var forksCount: Int,

        var openIssues: Int
) : Parcelable