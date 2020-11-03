package mazer.arthur.gamingshop.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GameDetails(
    @SerializedName("id") val idGameDetails: Int,
    val title: String,
    val publisher: String,
    val image: String,
    val discount: Int,
    val price: Float,
    val description: String,
    val rating: Double,
    val stars: Int,
    val reviews: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readInt(),
        source.readFloat(),
        source.readString()!!,
        source.readDouble(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(idGameDetails)
        writeString(title)
        writeString(publisher)
        writeString(image)
        writeInt(discount)
        writeFloat(price)
        writeString(description)
        writeDouble(rating)
        writeInt(stars)
        writeInt(reviews)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GameDetails> = object : Parcelable.Creator<GameDetails> {
            override fun createFromParcel(source: Parcel): GameDetails = GameDetails(source)
            override fun newArray(size: Int): Array<GameDetails?> = arrayOfNulls(size)
        }
    }
}