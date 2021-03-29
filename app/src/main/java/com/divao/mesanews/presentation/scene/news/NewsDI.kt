package com.divao.mesanews.presentation.scene.news

import com.divao.mesanews.presentation.common.navigation.FlowComponent
import com.divao.mesanews.common.di.PerScene
import com.divao.mesanews.data.repository.NewsRepository
import com.divao.mesanews.domain.usecase.GetFavoriteNewsListUC
import com.divao.mesanews.domain.usecase.GetNewsListUC
import com.divao.mesanews.domain.usecase.UpsertFavoriteIdsUC
import com.divao.mesanews.domain.usecase.UpsertFavoriteNewsUC
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class NewsModule(private val view: NewsView) {

    @Provides
    @PerScene
    fun provideNewsPresenter(
        getNewsListUC: GetNewsListUC,
        getFavoriteNewsListUC: GetFavoriteNewsListUC,
        upsertFavoriteIdsUC: UpsertFavoriteIdsUC,
        upsertFavoriteNewsUC: UpsertFavoriteNewsUC
    ): NewsPresenter {
        return NewsPresenter(
            view,
            getNewsListUC,
            getFavoriteNewsListUC,
            upsertFavoriteIdsUC,
            upsertFavoriteNewsUC
        )
    }
}

@Component(modules = [NewsModule::class], dependencies = [FlowComponent::class])
@PerScene
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}