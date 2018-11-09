package tamas.marton.gittrend.api.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName


data class Owner(

        var login: String,

        var id: Int,

        @SerializedName("avatar_url")
        var avatarUrl: String,

        @SerializedName("gravatar_id")
        var gravatarId: String,

        var url: String,

        @SerializedName("received_events_url")
        var receivedEventsUrl: String,

        var type: String
) {

    @Ignore
    constructor() : this("", 0, "", "", "", "", "")
}
