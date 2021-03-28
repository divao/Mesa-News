package com.divao.mesanews

import android.content.Context
import com.divao.mesanews.di.ApplicationContext
import com.divao.mesanews.model.MesaApi
import com.divao.mesanews.model.MesaService
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
    fun provideMesaApi(retrofitBuilder: Retrofit): MesaApi {
        return retrofitBuilder.create(MesaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMesaService(mesaApi: MesaApi): MesaService {
        return MesaService(mesaApi)
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun context() = context
}

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun exportMesaService(): MesaService

    @ApplicationContext
    fun context(): Context
}