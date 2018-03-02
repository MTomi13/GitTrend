package tamas.marton.gittrend.api.model

import com.google.gson.annotations.SerializedName


data class Repository(

        @SerializedName("total_count")
        var totalCount: Int,

        @SerializedName("incomplete_results")
        var incomplete_results: Boolean,

        var items: List<Item>
)