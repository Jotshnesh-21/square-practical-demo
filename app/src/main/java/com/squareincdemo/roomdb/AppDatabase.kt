package com.squareincdemo.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareincdemo.model.RepoModel

@Database(entities = [RepoModel::class], version = 1)
abstract class AppDatabase  : RoomDatabase() {

    abstract fun repoDao() : GitReposDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "TRENDING_REPO_DB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}