package com.squareincdemo.network

import com.squareincdemo.model.RepoModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(REPOSITORIES)
    suspend fun getGitRepoList(): Response<ArrayList<RepoModel>>

    companion object{
        const val REPOSITORIES = "orgs/square/repos"
    }

}