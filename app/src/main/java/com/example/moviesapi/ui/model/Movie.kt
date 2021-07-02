package com.example.moviesapi.ui.model

import android.os.Parcel
import android.os.Parcelable

/**
 * A model class, like a POJO, to avoid formatting data in different places,
 * domain package has a transform for this one
 */
data class Movie(
    val id: Long,
    val title: String,
    val genres: String,
    val overview: String,
    val runtime: String,
    val releaseYear: String,
    val rating: Int,
    val popularity: Double,
    var topRated: Boolean = false,
    val movieImgUrl: String
): Parcelable {

    fun getExtraData(): String {
        return releaseYear + " • " + genres + " • " + runtime
    }


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(genres)
        parcel.writeString(overview)
        parcel.writeString(runtime)
        parcel.writeString(releaseYear)
        parcel.writeInt(rating)
        parcel.writeDouble(popularity)
        parcel.writeByte(if (topRated) 1 else 0)
        parcel.writeString(movieImgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}