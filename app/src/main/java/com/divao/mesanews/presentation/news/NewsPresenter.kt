package com.divao.mesanews.presentation.news

import com.divao.mesanews.model.MesaService
import com.divao.mesanews.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers.*

class NewsPresenter(private val view: NewsView, private val mesaService: MesaService) {

    private val disposable = CompositeDisposable()

    fun fetchNews() {
        view.displayLoading()
        disposable.add(
            mesaService.getNewsList()
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<News>>() {
                    override fun onSuccess(value: List<News>) {
                        view.displayNewsList(value)
                        view.dismissLoading()
                    }

                    override fun onError(e: Throwable?) {
                        view.displayError()
                        view.dismissLoading()
                    }
                }
        ))
    }

    fun onViewDestroyed() {
        disposable.clear()
    }
}