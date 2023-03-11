package com.example.gallery.data.remote

import com.example.gallery.BuildConfig
import com.example.gallery.models.SearchResult
import com.example.gallery.models.UnSplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnSplashApi {


    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("search/photos")
    suspend fun searchImages(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): SearchResult


}





