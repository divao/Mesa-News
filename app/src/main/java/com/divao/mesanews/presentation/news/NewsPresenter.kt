package com.divao.mesanews.presentation.news

import com.divao.mesanews.model.NewsRepository
import com.divao.mesanews.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers.*

class NewsPresenter(private val view: NewsView, private val newsRepository: NewsRepository) {

    private val disposable = CompositeDisposable()

    init {
        view.onViewLoaded.doOnNext {
            view.displayLoading()
        }.flatMapSingle { filterByFavorites ->
            if (filterByFavorites) {
                newsRepository.getFavoriteNewsList()
                    .subscribeOn(newThread())
                    .observeOn(AndroidSchedulers.mainThread())
            } else {
                newsRepository.getNewsList()
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
            .subscribe({ newsList ->
                view.displayNewsList(newsList)
                view.dismissLoading()
            }, {
                view.displayError()
                view.dismissLoading()
            }).addTo(disposable)
    }

    fun setFavorite(news: News) {
        newsRepository.upsertFavoriteNews(news).subscribe().addTo(disposable)
        newsRepository.upsertFavoriteIds(news.title).subscribe().addTo(disposable)
    }

    fun onViewDestroyed() {
        disposable.clear()
    }


}