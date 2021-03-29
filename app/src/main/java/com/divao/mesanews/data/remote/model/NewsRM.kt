package com.divao.mesanews.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsRM(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("image_url")
    val imageUrl: String
)