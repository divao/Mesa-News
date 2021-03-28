package com.divao.mesanews.presentation.news

import com.divao.mesanews.model.MesaService
import com.divao.mesanews.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers.*

class NewsPresenter(private val view: NewsView, private val mesaService: MesaService) {

    private val disposable = CompositeDisposable()

    fun fetchNews() {
        view.displayLoading()
        disposable.add(
            mesaService.getNewsList()
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newsList ->
                    view.displayNewsList(newsList)
                    view.dismissLoading()
                }, {
                    view.displayError()
                    view.dismissLoading()
                })
        )
    }

    fun setFavorite(newsId: String) {
        mesaService.setFavorite(newsId).subscribe().addTo(disposable)
    }

    fun onViewDestroyed() {
        disposable.clear()
    }
}