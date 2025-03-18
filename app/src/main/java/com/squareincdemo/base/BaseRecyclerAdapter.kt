package com.squareincdemo.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerAdapter<T,VB:ViewBinding>(val inflate : (LayoutInflater,ViewGroup?,Boolean)->VB): Adapter<BaseRecyclerAdapter.ViewHolder<VB>>() {

    class ViewHolder<VB:ViewBinding>(val binding:VB): RecyclerView.ViewHolder(binding.root)

    private var mArrayList : ArrayList<T> = ArrayList()
    private var mCopyArrayList : ArrayList<T> = ArrayList()
    private var mContext : Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        mContext = parent.context
        return ViewHolder(inflate.invoke(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = mArrayList.size

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        fillItemData(holder.binding,getItemAtPosition(position),position)
    }

    abstract fun fillItemData(binding:VB,item:T,position:Int)

    fun getItemAtPosition(position:Int) = mArrayList[position]

    fun addAll(@NonNull list:ArrayList<T>){
        mArrayList=list
        notifyDataSetChanged()
    }

    fun clear(){
        mArrayList.clear()
        notifyDataSetChanged()
    }

    fun getContext():Context{
        return mContext!!
    }

    fun getAll():ArrayList<T>{
        return mArrayList
    }
}
