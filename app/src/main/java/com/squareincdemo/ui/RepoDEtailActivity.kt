package com.squareincdemo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.squareincdemo.R
import com.squareincdemo.base.BaseActivity
import com.squareincdemo.databinding.ActivityRepoDetailBinding
import com.squareincdemo.model.RepoModel
import com.squareincdemo.model.Resource
import com.squareincdemo.utill.Constant
import com.squareincdemo.utill.serializable
import com.squareincdemo.utill.showSnackBar

class RepoDEtailActivity :
    BaseActivity<ActivityRepoDetailBinding>(ActivityRepoDetailBinding::inflate) {
    private val mMainActivityViewModel by viewModels<MainViewModel>()
    private var repoDataModel: RepoModel? = null
    private var repoDataId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoDataModel = intent.serializable(Constant.REPO_DATA) as RepoModel?
        repoDataId = intent.getLongExtra(Constant.REPO_DATA_ID, 0)
        mMainActivityViewModel.getRepoByIdData(this, repoDataId)
        setBackPressed()
        observerHandler()
        setOnClickListener()
    }

    private fun observerHandler() {
        mMainActivityViewModel.repoDetailLiveData.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    shimmerAction(true)
                }

                is Resource.Success -> {
                    shimmerAction(false)
                    it.let {
                        it.data?.let { it1 ->
                            repoDataModel = it1
                            with(binding) {
                                txtRepoName.text = it1.name
                                txtDescription.text = it1.description
                                txtTotalStars.text = it1.stargazers_count.toString()
                                imgBookmark.setImageResource(if (it1.bookmarks) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark)
                            }
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

    private fun shimmerAction(isStart: Boolean? = false) {
        if (isStart == true) {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmer()
        } else {
            binding.shimmerLayout.apply {
                stopShimmer()
                visibility = View.GONE
            }
        }
    }

    private fun setOnClickListener() {
        binding.imgBookmark.setOnClickListener {
            val isBookmark: Boolean = repoDataModel?.bookmarks ?: false
            mMainActivityViewModel.bookmarkRepo(this, repoDataModel?.id ?: 0, isBookmark.not())
            repoDataModel?.bookmarks = isBookmark.not()
            binding.imgBookmark.setImageResource(if (isBookmark.not()) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark)
            if (isBookmark.not()) {
                binding.main.showSnackBar("Bookmarked")
            } else {
                binding.main.showSnackBar("Bookmarked Removed")
            }
        }
        binding.layoutHeader.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val result = Intent()
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        })
    }
}