package com.divao.mesanews.presentation.news

import com.divao.mesanews.cicerone.FlowComponent
import com.divao.mesanews.di.PerScene
import com.divao.mesanews.model.NewsRepository
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class NewsModule(private val view: NewsView) {

    @Provides
    @PerScene
    fun provideNewsPresenter(newsRepository: NewsRepository): NewsPresenter {
        return NewsPresenter(view, newsRepository)
    }
}

@Component(modules = [NewsModule::class], dependencies = [FlowComponent::class])
@PerScene
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}