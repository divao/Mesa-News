package com.divao.mesanews.presentation.scene.news

import io.reactivex.Observable

interface NewsView {

    fun displayLoading()

    fun dismissLoading()

    fun displayNewsList(newsList : List<NewsVM>)

    fun displayError()

    val onViewLoaded: Observable<Boolean>
}