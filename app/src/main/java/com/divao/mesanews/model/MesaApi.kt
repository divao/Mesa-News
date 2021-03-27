package com.divao.mesanews.model

import io.reactivex.Single
import retrofit2.http.*

const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjU5LCJlbWFpbCI6ImRpbWFzLmdhYnJpZWxAenJvYmFuay5jb20uYnIifQ.a3j7sRx8FIedZCfDGLocduOYpcibfIenX7TVJjv6Sis"

interface MesaApi {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $TOKEN"
    )
    @GET("v1/client/news?current_page=&per_page=&published_at=")
    fun getNewsPage(): Single<NewsPage>

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $TOKEN"
    )
    @GET("v1/client/news/highlights")
    fun getHighlights(): Single<List<News>>

//    @Headers("Content-Type: application/json")
//    @POST("v1/client/auth/signup")
//    fun signUp(@Body body: SignUpBody): Single<String>
//
//    @Headers("Content-Type: application/json")
//    @POST("v1/client/auth/signin")
//    fun signIn(@Body body: SignInBody): Single<String>
}