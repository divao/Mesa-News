package com.divao.mesanews.presentation.news

import com.divao.mesanews.ApplicationComponent
import com.divao.mesanews.model.MesaService
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class NewsModule(private val view: NewsView) {

    @Provides
    fun provideNewsPresenter(mesaService: MesaService): NewsPresenter {
        return NewsPresenter(view, mesaService)
    }
}

@Component(modules = [NewsModule::class], dependencies = [ApplicationComponent::class])
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}