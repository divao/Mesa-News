package com.divao.mesanews.data.cache.model

data class NewsCM(
    val title: String,
    val description: String,
    val publishedAt: String,
    val imageUrl: String,
    var isFavorite: Boolean
)