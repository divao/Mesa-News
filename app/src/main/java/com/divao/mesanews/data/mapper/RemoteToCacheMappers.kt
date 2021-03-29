package com.divao.mesanews.data.mapper

import com.divao.mesanews.data.cache.model.NewsCM
import com.divao.mesanews.data.remote.model.NewsRM

fun NewsRM.toCM(isFavorite: Boolean): NewsCM = NewsCM(title, description, publishedAt, imageUrl, isFavorite)