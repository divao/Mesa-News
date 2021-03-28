package com.divao.mesanews.model

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MesaService(private val api: MesaApi) {

    fun getNewsList(): Single<List<News>> {
        val newsPage = api.getNewsPage()
        return newsPage.flatMap { page -> Single.just(page.newsList) }.flatMap { newsList ->
            getFavorites().map { favorites ->
                newsList.map { news ->
                    news.copy(
                        isFavorite = favorites.contains(news.title)
                    )
                }
            }
        }
    }

    fun getFavorites(): Single<List<String>> =
        RxPaperBook.with().read<List<String>>(Tags().FAVORITES).onErrorReturn { emptyList() }

    fun setFavorite(newsId: String): Completable =
        getFavorites().flatMapCompletable { list ->
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
            RxPaperBook.with().write(Tags().FAVORITES, favorites)
        }

    private inner class Tags {
        val FAVORITES = "Favorites"
    }
}