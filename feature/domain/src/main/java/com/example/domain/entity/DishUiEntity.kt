package com.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DishUiEntity(
    val id: Int,
    val name: String,
    val price: Double,
    val weight: Double,
    val description: String,
    val imageUrl: String,
    val tags: List<String>
) : Parcelable