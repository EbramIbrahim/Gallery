package com.example.gallery.di

import com.example.gallery.data.remote.UnSplashApi
import com.example.gallery.data.repository.UnSplashRepositoryImpl
import com.example.gallery.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInstance(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUnSplashImageApi(retrofit: Retrofit): UnSplashApi {
        return retrofit.create(UnSplashApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUnSplashImageRepository(
        api: UnSplashApi,
    ): UnSplashRepositoryImpl{
        return UnSplashRepositoryImpl(api)
    }


}





