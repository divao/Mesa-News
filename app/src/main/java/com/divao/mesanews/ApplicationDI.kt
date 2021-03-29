package com.divao.mesanews

import android.content.Context
import com.divao.mesanews.di.ApplicationContext
import com.divao.mesanews.model.NewsCDS
import com.divao.mesanews.model.NewsRDS
import com.divao.mesanews.model.NewsRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    companion object {
        private val BASE_URL = "https://mesa-news-api.herokuapp.com"
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsRDS(retrofitBuilder: Retrofit): NewsRDS {
        return retrofitBuilder.create(NewsRDS::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsCDS(): NewsCDS {
        return NewsCDS()
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsRDS: NewsRDS, newsCDS: NewsCDS): NewsRepository {
        return NewsRepository(newsRDS, newsCDS)
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun context() = context
}

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun exportNewsRepository(): NewsRepository

    @ApplicationContext
    fun context(): Context
}