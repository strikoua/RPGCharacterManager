package com.example.dndcharactersheetmanager.apiCalls

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassDetails(
    val index: String,
    val name: String,
    val hit_die: Int
) : Parcelable