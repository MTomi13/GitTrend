package tamas.marton.gittrend.details

import android.os.Parcel
import android.os.Parcelable


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
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeString(type)
        parcel.writeString(avatarUrl)
        parcel.writeString(lastUpdated)
        parcel.writeString(created)
        parcel.writeInt(size)
        parcel.writeInt(starsCount)
        parcel.writeInt(watchersCount)
        parcel.writeInt(forksCount)
        parcel.writeInt(openIssues)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailsUIModel> {
        override fun createFromParcel(parcel: Parcel): DetailsUIModel {
            return DetailsUIModel(parcel)
        }

        override fun newArray(size: Int): Array<DetailsUIModel?> {
            return arrayOfNulls(size)
        }
    }
}