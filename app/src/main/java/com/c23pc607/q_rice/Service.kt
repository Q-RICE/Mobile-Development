package com.c23pc607.q_rice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    val name: String,
    val icon: Int
) : Parcelable