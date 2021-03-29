package com.divao.mesanews.data.remote.model

import com.google.gson.annotations.SerializedName

data class PaginationRM(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_items")
    val totalItems: Int
)