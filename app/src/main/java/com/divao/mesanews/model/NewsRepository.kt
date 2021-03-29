package com.divao.mesanews.model

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(private val rds: NewsRDS, private val cds: NewsCDS) {

    fun getNewsList(): Single<List<News>> {
        val newsPage = rds.getNewsPage()
        return newsPage.flatMap { page -> Single.just(page.newsList) }.flatMap { newsList ->
            cds.getFavoriteIds().map { favorites ->
                newsList.map { news ->
                    news.copy(
                        isFavorite = favorites.contains(news.title)
                    )
                }
            }
        }
    }

    fun getFavoriteNewsList(): Single<List<News>> = cds.getFavoriteNews()

    fun upsertFavoriteNews(news: News): Completable = cds.upsertFavoriteNews(news)

    fun upsertFavoriteIds(newsId: String): Completable = cds.upsertFavoriteIds(newsId)

}