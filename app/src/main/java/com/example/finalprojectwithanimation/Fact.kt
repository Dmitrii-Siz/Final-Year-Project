package com.example.finalprojectwithanimation

import android.os.Parcel
import android.os.Parcelable

//Creates a class for the facts:

//Parcelable interface (Now the class can be passed around different Fragments without any issues)
data class Fact(val text: String, val answer: String , val isTrue: Boolean) : Parcelable {
    constructor(p: Parcel) : this(
        p.readString()!!,
        p.readString()!!,
        p.readByte() != 0.toByte()
    )

    override fun writeToParcel(p: Parcel, flags: Int) {
        p.writeString(text)
        p.writeString(answer)
        p.writeByte(if (isTrue) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fact> {
        override fun createFromParcel(p: Parcel): Fact {
            return Fact(p)
        }

        override fun newArray(size: Int): Array<Fact?> {
            return arrayOfNulls(size)
        }
    }
}
