package com.example.the_medallists

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheMedalistData(
    val country_name: String,
    val country_code: String,
    val time_competed: Int,
    val gold: Int,
    val silver: Int,
    val bronze: Int,
    val total_medal: Int
) : Parcelable
