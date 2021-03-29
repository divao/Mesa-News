package com.divao.mesanews.data.remote

import com.divao.mesanews.data.remote.model.NewsPageRM
import com.divao.mesanews.data.remote.model.NewsRM
import io.reactivex.Single
import retrofit2.http.*

const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjU5LCJlbWFpbCI6ImRpbWFzLmdhYnJpZWxAenJvYmFuay5jb20uYnIifQ.a3j7sRx8FIedZCfDGLocduOYpcibfIenX7TVJjv6Sis"

interface NewsRDS {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $TOKEN"
    )
    @GET("v1/client/news?current_page=&per_page=&published_at=")
    fun getNewsPage(): Single<NewsPageRM>
}