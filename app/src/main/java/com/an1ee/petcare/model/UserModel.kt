package com.an1ee.petcare.model

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    val id: String = "", // Add ID field
    var fullName: String = "",
    var email: String = "",
    var password: String = "",
    var role: String = "USER", // Standardize role names
    var createdAt: Long = System.currentTimeMillis(),
    var lastLogin: Long = System.currentTimeMillis()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}