package com.movies.data.database

import com.movies.data.database.converters.DateConverter
import com.movies.data.database.dao.PopularDao
import com.movies.data.database.entities.PopularEntry
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PopularEntry::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CacheDatabase: RoomDatabase() {

    companion object {
        private val LOG_TAG: String = CacheDatabase::class.simpleName.toString()
        private val LOCK: Any = Object()
        private val DATABSE_NAME: String = "movies"
        @Volatile
        private var sInstance: CacheDatabase? = null

        fun getInstance(context: Context): CacheDatabase{
            if (sInstance == null){
                synchronized(LOCK){
                    sInstance = Room.databaseBuilder(context.applicationContext,
                            CacheDatabase::class.java,CacheDatabase.DATABSE_NAME)
                            .build()
                }
            }
            return sInstance!!
        }

    }


    abstract fun poplarDao(): PopularDao


}