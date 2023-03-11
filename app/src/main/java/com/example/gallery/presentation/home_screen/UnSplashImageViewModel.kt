package com.example.gallery.presentation.home_screen

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.gallery.data.repository.UnSplashRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow


import javax.inject.Inject


@HiltViewModel
class UnSplashImageViewModel @Inject constructor(
    private val repository: UnSplashRepositoryImpl,
    state: SavedStateHandle
) : ViewModel(), LifecycleObserver {



     private val searchQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val searchImages = searchQuery.switchMap { query ->
        repository.searchImages(query)
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }



    companion object {
        const val CURRENT_QUERY = "current_query"
        const val DEFAULT_QUERY = "random"
    }


}





