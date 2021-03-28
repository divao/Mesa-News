package com.divao.mesanews.model

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single

class MesaService(private val api: MesaApi) {

    fun getNewsList(): Single<List<News>> {
        val newsPage = api.getNewsPage()
        return newsPage.flatMap { page -> Single.just(page.newsList) }.flatMap { newsList ->
            getFavoriteIds().map { favorites ->
                newsList.map { news ->
                    news.copy(
                        isFavorite = favorites.contains(news.title)
                    )
                }
            }
        }
    }

    fun getFavoriteNewsList(): Single<List<News>> = getFavoriteNews()

    private fun getFavoriteNews(): Single<List<News>> =
        RxPaperBook.with().read<List<News>>(Tags().FAVORITE_NEWS).onErrorReturn { emptyList() }

    fun setFavoriteNews(news: News): Completable =
        getFavoriteNews().flatMapCompletable { list ->
            val updatedList = if (list.map { it.title }.contains(news.title)) {
                list.filter { it.title != news.title }
            } else {
                list + listOf(news)
            }
            RxPaperBook.with().write(Tags().FAVORITE_NEWS, updatedList)
        }

    private fun getFavoriteIds(): Single<List<String>> =
        RxPaperBook.with().read<List<String>>(Tags().FAVORITE_IDS).onErrorReturn { emptyList() }

    fun setFavoriteIds(newsId: String): Completable =
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