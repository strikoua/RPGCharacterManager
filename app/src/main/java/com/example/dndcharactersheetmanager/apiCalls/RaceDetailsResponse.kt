package com.example.dndcharactersheetmanager.apiCalls

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RaceDetailsResponse(
    val index: String,
    val name: String
) : Parcelable