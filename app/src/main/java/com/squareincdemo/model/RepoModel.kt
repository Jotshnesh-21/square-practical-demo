package com.squareincdemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "repo_table")
data class RepoModel(
    @PrimaryKey(autoGenerate = true) val idN: Int?=0,
    @ColumnInfo(name ="id", defaultValue = "0") val id: Long?,
    @ColumnInfo(name ="name", defaultValue = "") val name: String?,
    @ColumnInfo(name ="full_Name", defaultValue = "") val fullName: String?,
    @ColumnInfo(name ="description", defaultValue = "") val description: String?,
    @ColumnInfo(name ="stargazers_count", defaultValue = "0") val stargazers_count: Int?,
    @ColumnInfo(name ="bookmarks",defaultValue = "false") var bookmarks: Boolean
): Serializable