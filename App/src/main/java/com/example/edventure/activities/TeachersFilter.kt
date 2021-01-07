package com.example.edventure.activities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.security.auth.Subject
@Parcelize
data class TeachersFilter(
    //TODO: Vytvořit všem classu
    var subject: String = "",
    var city: String = "",
    var rating: Double = -1.0,
    var minPrice: Double = -1.0,
    var maxPrice: Double = -1.0
) : Parcelable