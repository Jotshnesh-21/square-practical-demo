package com.squareincdemo.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val inflate:(LayoutInflater) -> VB) : AppCompatActivity(){

    lateinit var binding : VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = if (Build.VERSION.SDK_INT ==26) ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
    }
}