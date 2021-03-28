package com.divao.mesanews.model

import com.google.gson.annotations.SerializedName

data class NewsPage(
    @SerializedName("pagination")
    val pagination: Pagination?,
    @SerializedName("data")
    val newsList: List<News>
)