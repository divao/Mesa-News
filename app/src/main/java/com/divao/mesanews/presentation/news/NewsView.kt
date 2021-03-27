package com.divao.mesanews.presentation.news

import com.divao.mesanews.model.News

interface NewsView {

    fun displayLoading()

    fun dismissLoading()

    fun displayNewsList(newsList : List<News>)

    fun displayError()
}