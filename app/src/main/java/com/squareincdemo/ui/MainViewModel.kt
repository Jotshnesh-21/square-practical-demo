package com.squareincdemo.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareincdemo.model.RepoModel
import com.squareincdemo.model.Resource
import com.squareincdemo.network.RetrofitClient
import com.squareincdemo.roomdb.DBRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel()  {

    val repoLiveData = MutableLiveData<Resource<ArrayList<RepoModel>>>()
    val repoDetailLiveData = MutableLiveData<Resource<RepoModel>>()
    private val repoBookmarkLiveData = MutableLiveData<Resource<String>>()

    private fun getGitRepoList(context: Context){
        viewModelScope.launch {
            val response = RetrofitClient.apiService.getGitRepoList()
            if (response.isSuccessful){
                response.body()?.let {
                    DBRepository.insertData(context, it)
                    repoLiveData.value = Resource.Success(it)
                }
            }else{
                repoLiveData.value = Resource.Error("Something went wrong")
            }

        }
    }

    fun bookmarkRepo(context: Context,id:Long,isBookmark:Boolean) {
        repoBookmarkLiveData.value = Resource.Loading()
        viewModelScope.launch {
            DBRepository.bookmarkRepo(context,id, isBookmark)
            repoBookmarkLiveData.value = Resource.Success("Bookmarked")
        }
    }

    fun getRepoData(context: Context) {
        repoLiveData.value = Resource.Loading()
        viewModelScope.launch {
            val list = DBRepository.getRepoData(context) as ArrayList<RepoModel>
            if (list.isEmpty()){
                getGitRepoList(context)
            }else{
                repoLiveData.value = Resource.Success(list)
            }
        }
    }

    fun getRepoByIdData(context: Context,id:Long) {
        repoDetailLiveData.value = Resource.Loading()
        viewModelScope.launch {
            val data = DBRepository.getRepoById(context,id) as RepoModel
            repoDetailLiveData.value = Resource.Success(data)
        }
    }
}