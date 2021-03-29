package com.divao.mesanews

import android.content.Context
import android.util.Log
import com.divao.mesanews.common.di.ApplicationContext
import com.divao.mesanews.data.cache.NewsCDS
import com.divao.mesanews.data.remote.NewsRDS
import com.divao.mesanews.data.repository.NewsRepository
import com.divao.mesanews.domain.di.BackgroundScheduler
import com.divao.mesanews.domain.di.MainScheduler
import com.divao.mesanews.domain.utility.Logger
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    @Provides
    @BackgroundScheduler
    fun backgroundScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @MainScheduler
    fun mainScheduler() = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun logger() = object : Logger {
        override fun log(t: Throwable) {
            Log.e("Applicou Exception: ", "", t)
        }
    }
}

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun exportNewsRepository(): NewsRepository

    @ApplicationContext
    fun context(): Context

    @BackgroundScheduler
    fun backgroundScheduler(): Scheduler

    @MainScheduler
    fun mainScheduler(): Scheduler

    fun logger(): Logger
}