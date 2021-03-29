package com.divao.mesanews.presentation.news

import com.divao.mesanews.model.MesaService
import com.divao.mesanews.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers.*

class NewsPresenter(private val view: NewsView, private val mesaService: MesaService) {

    private val disposable = CompositeDisposable()

    init {
        view.onViewLoaded.doOnNext {
            view.displayLoading()
        }.flatMapSingle { filterByFavorites ->
            if (filterByFavorites) {
                mesaService.getFavoriteNewsList()
                    .subscribeOn(newThread())
                    .observeOn(AndroidSchedulers.mainThread())
            } else {
                mesaService.getNewsList()
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
        mesaService.setFavoriteNews(news).subscribe().addTo(disposable)
        mesaService.setFavoriteIds(news.title).subscribe().addTo(disposable)
    }

    fun onViewDestroyed() {
        disposable.clear()
    }


}