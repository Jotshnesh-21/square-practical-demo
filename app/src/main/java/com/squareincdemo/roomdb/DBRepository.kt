package com.squareincdemo.roomdb

import android.content.Context
import com.squareincdemo.model.RepoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DBRepository {

    companion object {
        private var appDatabase: AppDatabase? = null
        private var listRepoData: List<RepoModel>? = null
        private var repoData: RepoModel? = null

        private fun initialisationDb(context: Context): AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        fun bookmarkRepo(context: Context,id:Long,isBookmark:Boolean) {
            appDatabase = initialisationDb(context)
            CoroutineScope(IO).launch {
                appDatabase?.repoDao()?.bookmarkRepo(id,isBookmark)
            }
        }

        fun insertData(context: Context, repoData: List<RepoModel>) {
            appDatabase = initialisationDb(context)
            CoroutineScope(IO).launch {
                appDatabase?.repoDao()?.insertData(repoData)
            }
        }

        suspend fun getRepoData(context: Context): List<RepoModel>? {
            appDatabase = initialisationDb(context)
            listRepoData = appDatabase?.repoDao()?.getRepoList()
            return listRepoData
        }

        suspend fun getRepoById(context: Context,id:Long): RepoModel? {
            appDatabase = initialisationDb(context)
            repoData = appDatabase?.repoDao()?.getRepoById(id)
            return repoData
        }

    }

}