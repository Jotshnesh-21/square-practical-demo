package com.squareincdemo.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.squareincdemo.model.RepoModel

@Dao
interface GitReposDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(repo: List<RepoModel>)

    @Query("SELECT * FROM repo_table ORDER BY stargazers_count DESC")
    suspend fun getRepoList() : List<RepoModel>

    @Query("UPDATE repo_table SET bookmarks = :bookmark WHERE id = :id")
    suspend fun bookmarkRepo(id: Long, bookmark: Boolean)


    @Query("SELECT * FROM repo_table WHERE id = :id")
    suspend fun getRepoById(id: Long): RepoModel
}