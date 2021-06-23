package com.fphoenixcorneae.aspectj.demo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.aspectj.demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var mViewBinding: ActivityMainBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding!!.root)
        mViewBinding?.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }
}