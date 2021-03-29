package com.divao.mesanews.presentation.scene.news

import com.divao.mesanews.data.repository.NewsRepository
import com.divao.mesanews.data.remote.model.NewsRM
import com.divao.mesanews.domain.usecase.GetFavoriteNewsListUC
import com.divao.mesanews.domain.usecase.GetNewsListUC
import com.divao.mesanews.domain.usecase.UpsertFavoriteIdsUC
import com.divao.mesanews.domain.usecase.UpsertFavoriteNewsUC
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers.*

class NewsPresenter(
    private val view: NewsView,
    private val getNewsListUC: GetNewsListUC,
    private val getFavoriteNewsListUC: GetFavoriteNewsListUC,
    private val upsertFavoriteIdsUC: UpsertFavoriteIdsUC,
    private val upsertFavoriteNewsUC: UpsertFavoriteNewsUC
) {

    private val disposable = CompositeDisposable()

    init {
        view.onViewLoaded.doOnNext {
            view.displayLoading()
        }.flatMapSingle { filterByFavorites ->
            if (filterByFavorites) {
                getFavoriteNewsListUC.getSingle(Unit)
                    .subscribeOn(newThread())
                    .observeOn(AndroidSchedulers.mainThread())
            } else {
                getNewsListUC.getSingle(Unit)
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
            .subscribe({ newsList ->
                view.displayNewsList(newsList.toVM())
                view.dismissLoading()
            }, {
                view.displayError()
                view.dismissLoading()
            }).addTo(disposable)
    }

    fun setFavorite(newsVM: NewsVM) {
        upsertFavoriteNewsUC.getCompletable(UpsertFavoriteNewsUC.Request(newsVM.toDM()))
            .subscribe()
            .addTo(disposable)
        upsertFavoriteIdsUC.getCompletable(UpsertFavoriteIdsUC.Request(newsVM.title))
            .subscribe()
            .addTo(disposable)
    }

    fun onViewDestroyed() {
        disposable.clear()
    }


}