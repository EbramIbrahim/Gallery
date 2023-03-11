package com.example.gallery.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gallery.data.remote.UnSplashApi
import com.example.gallery.models.UnSplashImage
import com.example.gallery.utils.Constant.FIRST_PAGE_INDEX
import com.example.gallery.utils.Constant.ITEMS_PER_PAGE
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max

class SearchPagingSource(
    private val api: UnSplashApi,
    private val query: String
): PagingSource<Int, UnSplashImage>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashImage> {
        val startKey = params.key ?: FIRST_PAGE_INDEX

        if (startKey != FIRST_PAGE_INDEX) delay(3000L)

        return try {
            val response = api.searchImages(query = query, perPage = ITEMS_PER_PAGE, page = startKey)
            val result = response.results

                LoadResult.Page(
                    data = result,
                    prevKey = if (startKey == FIRST_PAGE_INDEX) null else startKey - 1,
                    nextKey = if (result.isEmpty()) null else startKey + 1
                )

        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnSplashImage>): Int? {
        return state.anchorPosition
    }

}












