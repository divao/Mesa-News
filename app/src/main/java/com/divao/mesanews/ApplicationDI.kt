package com.divao.mesanews

import com.divao.mesanews.model.MesaApi
import com.divao.mesanews.model.MesaService
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule {
    companion object {
        private val BASE_URL = "https://mesa-news-api.herokuapp.com"
    }

    @Provides
    fun provideRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideMesaApi(retrofitBuilder: Retrofit): MesaApi {
        return retrofitBuilder.create(MesaApi::class.java)
    }

    @Provides
    fun provideMesaService(mesaApi: MesaApi): MesaService {
        return MesaService(mesaApi)
    }
}

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun exportMesaService(): MesaService
}