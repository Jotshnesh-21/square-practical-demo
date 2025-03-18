package com.squareincdemo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.squareincdemo.base.BaseActivity
import com.squareincdemo.databinding.ActivityMainBinding
import com.squareincdemo.model.RepoModel
import com.squareincdemo.model.Resource
import com.squareincdemo.ui.adapter.AdapterRepoItem
import com.squareincdemo.utill.ConnectionManager
import com.squareincdemo.utill.Constant
import com.squareincdemo.utill.showSnackBar

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mMainActivityViewModel by viewModels<MainViewModel>()
    private val adapter = AdapterRepoItem()
    private val activityLauncherActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            checkInternetConnectivity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observerHandler()
        checkInternetConnectivity()
    }

    private fun initView() {
        with(binding){
            refreshLayout.setOnRefreshListener {
                rvTrendingList.visibility = View.GONE
                refreshLayout.isRefreshing = false
                checkInternetConnectivity()
            }
            layoutHeader.imgBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
                finish()
            }
        }
    }

    private fun observerHandler() {
        mMainActivityViewModel.repoLiveData.observe(this){
            when (it) {
                is Resource.Loading -> {
                    shimmerAction(true)
                }

                is Resource.Success -> {
                    shimmerAction(false)
                    it.let {
                        it.data?.let { it1 ->
                            setRepoListAdapter(it1)
                        }
                    }
                }

                is Resource.Error -> {
                    shimmerAction(false)
                }

                else -> {
                    shimmerAction(false)
                }
            }
        }
    }

    private fun setRepoListAdapter(list: ArrayList<RepoModel>) {
        with(binding){
            if (list.size > 0) {
                rvTrendingList.visibility = View.VISIBLE
                refreshLayout.visibility = View.VISIBLE
            } else {
                rvTrendingList.visibility = View.GONE
                refreshLayout.visibility = View.GONE
            }
            adapter.addAll(list)
            adapter.onItemClickListener = {_: Int, data: RepoModel ->
                val intent = Intent(this@MainActivity, RepoDEtailActivity::class.java)
                intent.putExtra(Constant.REPO_DATA_ID, data.id)
                intent.putExtra(Constant.REPO_DATA, data)
                activityLauncherActivity.launch(intent)
            }
            rvTrendingList.adapter = adapter
        }
    }

    private fun shimmerAction(isStart: Boolean?=false) {
        if (isStart == true) {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.refreshLayout.visibility = View.GONE
            binding.shimmerLayout.startShimmer()
        } else {
            binding.shimmerLayout.apply {
                stopShimmer()
                visibility = View.GONE
            }
            binding.refreshLayout.visibility = View.VISIBLE
        }
    }

    private fun checkInternetConnectivity() {
        val isConnected = ConnectionManager.isInternetConnectivityAvailable(this)
        if (!isConnected) {
            binding.main.showSnackBar("No Internet Connection")
        }
        mMainActivityViewModel.getRepoData(this)
    }
}