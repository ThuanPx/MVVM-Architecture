package com.thuanpx.mvvm_architecture.di

import android.app.Application
import com.google.gson.Gson
import com.thuanpx.mvvm_architecture.BuildConfig
import com.thuanpx.mvvm_architecture.data.local.datastore.PreferenceDataStore
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.data.remote.api.middleware.InterceptorImpl
import com.thuanpx.mvvm_architecture.utils.coroutines.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by ThuanPx on 8/7/20.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.END_POINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpCache(app: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        return Cache(app.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.cache(cache)
        httpClientBuilder.addInterceptor(interceptor)

        httpClientBuilder.readTimeout(
            READ_TIMEOUT, TimeUnit.SECONDS
        )
        httpClientBuilder.writeTimeout(
            WRITE_TIMEOUT, TimeUnit.SECONDS
        )
        httpClientBuilder.connectTimeout(
            CONNECTION_TIMEOUT, TimeUnit.SECONDS
        )

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            httpClientBuilder.addInterceptor(logging)
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(preferenceDataStore: PreferenceDataStore): Interceptor {
        return InterceptorImpl(preferenceDataStore)
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private const val READ_TIMEOUT: Long = 30
    private const val WRITE_TIMEOUT: Long = 30
    private const val CONNECTION_TIMEOUT: Long = 30
}
