package com.divao.mesanews.data.repository

import com.divao.mesanews.data.cache.NewsCDS
import com.divao.mesanews.data.mapper.toCM
import com.divao.mesanews.data.mapper.toDM
import com.divao.mesanews.data.mapper.toNewsListDM
import com.divao.mesanews.data.remote.NewsRDS
import com.divao.mesanews.data.remote.model.NewsRM
import com.divao.mesanews.domain.datarepository.NewsDataRepository
import com.divao.mesanews.domain.model.News
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(private val rds: NewsRDS, private val cds: NewsCDS) : NewsDataRepository{

    override fun getNewsList(): Single<List<News>> {
        val newsPage = rds.getNewsPage()
        return newsPage.flatMap { page -> Single.just(page.newsListRM) }.flatMap { newsListRM ->
            cds.getFavoriteIds().map { favorites ->
                newsListRM.map { news ->
                    news.toCM(isFavorite = favorites.contains(news.title)).toDM()
                }
            }
        }
    }

    override fun getFavoriteNewsList(): Single<List<News>> = cds.getFavoriteNews().map { it }

    override fun upsertFavoriteNews(news: News): Completable = cds.upsertFavoriteNews(news)

    override fun upsertFavoriteIds(newsId: String): Completable = cds.upsertFavoriteIds(newsId)

}