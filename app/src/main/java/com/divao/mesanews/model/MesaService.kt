package com.divao.mesanews.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MesaService {

    companion object {
        private val BASE_URL = "https://mesa-news-api.herokuapp.com"
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MesaApi::class.java)

    fun getNewsList(): Single<List<News>> {
        val newsPage = api.getNewsPage()
        return newsPage.flatMap { page -> Single.just(page.newsList) }
    }
}