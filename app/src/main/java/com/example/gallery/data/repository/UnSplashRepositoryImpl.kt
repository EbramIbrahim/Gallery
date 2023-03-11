package com.example.gallery.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.gallery.data.pagination.SearchPagingSource
import com.example.gallery.data.remote.UnSplashApi
import com.example.gallery.utils.Constant.ITEMS_PER_PAGE


import javax.inject.Inject


class UnSplashRepositoryImpl @Inject constructor(
    private val api: UnSplashApi,
) {

    fun searchImages(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = false,
            ), pagingSourceFactory = { SearchPagingSource(api, query) }
        ).liveData


}







