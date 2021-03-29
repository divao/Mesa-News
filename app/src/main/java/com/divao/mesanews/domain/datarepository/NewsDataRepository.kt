package com.divao.mesanews.domain.datarepository

import com.divao.mesanews.data.remote.model.NewsRM
import com.divao.mesanews.domain.model.News
import io.reactivex.Completable
import io.reactivex.Single

interface NewsDataRepository {
    fun getNewsList(): Single<List<News>>
    fun getFavoriteNewsList(): Single<List<News>>
    fun upsertFavoriteNews(news: News): Completable
    fun upsertFavoriteIds(newsId: String): Completable
}