package com.movies.ui.main.viewmodels

import com.movies.data.database.databaseResults.PopularResults
import com.movies.data.database.entities.PopularEntry
import com.movies.data.repositories.PopularRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class PopularViewModel(private val repository: PopularRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val popularResult: LiveData<PopularResults> = Transformations.map(queryLiveData) {
        repository.popular(it)
    }

    val popularMovies: LiveData<PagedList<PopularEntry>> = Transformations.switchMap(popularResult
    ) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(popularResult
    ) { it -> it.networkErrors }

    fun getPopular(region: String) {
        queryLiveData.value = region
    }

}