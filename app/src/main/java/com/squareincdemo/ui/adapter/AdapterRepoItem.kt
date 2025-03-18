package com.squareincdemo.ui.adapter

import com.squareincdemo.R
import com.squareincdemo.base.BaseRecyclerAdapter
import com.squareincdemo.databinding.AdapterRepoItemBinding
import com.squareincdemo.model.RepoModel

class AdapterRepoItem : BaseRecyclerAdapter<RepoModel,AdapterRepoItemBinding>(AdapterRepoItemBinding::inflate) {

    var onItemClickListener : ((position:Int,data:RepoModel)->Unit)? = null

    override fun fillItemData(binding: AdapterRepoItemBinding, item: RepoModel, position: Int) {
        with(binding){
            txtRepoName.text = item.name
            txtTotalStars.text = item.stargazers_count.toString()
            imgBookmark.setImageResource(if (item.bookmarks) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark)
            cardView.setOnClickListener {
                onItemClickListener?.invoke(position,item)
            }
        }
    }
}