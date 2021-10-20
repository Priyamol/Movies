package com.movies.data.database.localCache

import com.movies.data.database.dao.PopularDao
import com.movies.data.database.entities.PopularEntry
import androidx.paging.DataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

class PopularLocalCache(
    private val popularDao: PopularDao,
    private val ioExecutor: Executor
) {


    fun insert(repos: List<PopularEntry>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            popularDao.insert(repos)
            insertFinished()
        }
    }

    fun getAllPopular(): DataSource.Factory<Int, PopularEntry> {
        return popularDao.loadAllPopular()
    }

}