package com.reza.testingexample.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val name: String? = null,
    val capital: String? = null,
    val alpha2Code: String? = null,
    val region: String? = null,
    val subregion: String? = null
) : Parcelable
