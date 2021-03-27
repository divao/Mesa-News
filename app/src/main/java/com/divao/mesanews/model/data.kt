package com.divao.mesanews.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("highlight")
    val highlight: Boolean?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("image_url")
    val imageUrl: String?
)

data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_items")
    val totalItems: Int?
)

data class NewsPage(
    @SerializedName("pagination")
    val pagination: Pagination?,
    @SerializedName("data")
    val newsList: List<News>
)

//data class User(
//
//)