package com.divao.mesanews.domain.model

data class News(
    val title: String,
    val description: String,
    val publishedAt: String,
    val imageUrl: String,
    var isFavorite: Boolean
)