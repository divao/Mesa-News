package com.divao.mesanews.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsPageRM(
    @SerializedName("pagination")
    val paginationRM: PaginationRM,
    @SerializedName("data")
    val newsListRM: List<NewsRM>
)