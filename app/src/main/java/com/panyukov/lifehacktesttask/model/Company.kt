package com.panyukov.lifehacktesttask.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val id: Long,
    val name: String,
    val imgUrl: String) : Parcelable
