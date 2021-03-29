package com.divao.mesanews.presentation.scene.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsVM(
    val title: String,
    val description: String,
    val publishedAt: String,
    val imageUrl: String,
    var isFavorite: Boolean
) : Parcelable