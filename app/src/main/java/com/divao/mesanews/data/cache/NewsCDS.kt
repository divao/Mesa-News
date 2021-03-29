package com.divao.mesanews.data.cache

import com.divao.mesanews.data.cache.model.NewsCM
import com.divao.mesanews.data.mapper.toCM
import com.divao.mesanews.data.remote.model.NewsRM
import com.divao.mesanews.domain.model.News
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single

class NewsCDS {

    fun getFavoriteNews(): Single<List<News>> =
        RxPaperBook.with().read<List<News>>(Tags().FAVORITE_NEWS).onErrorReturn { emptyList() }

    fun upsertFavoriteNews(news: News): Completable =
        getFavoriteNews().flatMapCompletable { list ->
            val updatedList = if (list.map { it.title }.contains(news.title)) {
                list.filter { it.title != news.title }
            } else {
                list + listOf(news)
            }
            RxPaperBook.with().write(Tags().FAVORITE_NEWS, updatedList)
        }

    fun getFavoriteIds(): Single<List<String>> =
        RxPaperBook.with().read<List<String>>(Tags().FAVORITE_IDS).onErrorReturn { emptyList() }

    fun upsertFavoriteIds(newsId: String): Completable =
        getFavoriteIds().flatMapCompletable { list ->
            val favorites =
                if (list.contains(newsId)) {
                    list.mapNotNull {
                        if (it == newsId) {
                            null
                        } else {
                            it
                        }
                    }
                } else {
                    list + listOf(newsId)
                }
            RxPaperBook.with().write(Tags().FAVORITE_IDS, favorites)
        }

    private inner class Tags {
        val FAVORITE_IDS = "FavoriteIds"
        val FAVORITE_NEWS = "FavoriteNews"
    }

}