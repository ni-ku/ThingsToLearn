package de.niku.ttl.view.fragment_card_create

import android.os.Parcel
import android.os.Parcelable

class CardCreateResultData(
    val frontValue: String,
    val backValue: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(frontValue)
        parcel.writeString(backValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardCreateResultData> {
        override fun createFromParcel(parcel: Parcel): CardCreateResultData {
            return CardCreateResultData(parcel)
        }

        override fun newArray(size: Int): Array<CardCreateResultData?> {
            return arrayOfNulls(size)
        }
    }

}