package com.divao.mesanews.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MesaService(private val api: MesaApi) {

    fun getNewsList(): Single<List<News>> {
        val newsPage = api.getNewsPage()
        return newsPage.flatMap { page -> Single.just(page.newsList) }
    }
}