package com.movies.data.database.databaseResults

import com.movies.data.database.entities.PopularEntry
import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class PopularResults(
        val data: LiveData<PagedList<PopularEntry>>,
        val networkErrors: LiveData<String>
)