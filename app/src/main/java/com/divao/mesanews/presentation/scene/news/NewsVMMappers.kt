package com.divao.mesanews.presentation.scene.news

import com.divao.mesanews.domain.model.News

fun List<News>.toVM(): List<NewsVM> = map { it.toVM() }

fun News.toVM(): NewsVM = NewsVM(title, description, publishedAt, imageUrl, isFavorite)

fun NewsVM.toDM(): News = News(title, description, publishedAt, imageUrl, isFavorite)