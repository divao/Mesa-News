package com.divao.mesanews.data.mapper

import com.divao.mesanews.data.cache.model.NewsCM
import com.divao.mesanews.domain.model.News

fun List<NewsCM>.toNewsListDM(): List<News> = map { it.toDM() }

fun NewsCM.toDM(): News= News(title, description, publishedAt, imageUrl, isFavorite)