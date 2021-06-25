package com.fphoenixcorneae.aspectj.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.aspectj.AspectjHandler
import com.fphoenixcorneae.aspectj.demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mViewBinding: ActivityMainBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding!!.root)
        mViewBinding?.apply {
            lifecycleOwner = this@MainActivity
            onClick = this@MainActivity
        }

        AspectjHandler.init { cls, joinPoint ->
            Log.d(
                "AndroidAspectj",
                "notifyHandler() called with: clazz = [$cls], joinPoint = [$joinPoint]"
            )
            try {
                when (cls) {
                    OnClick::class.java -> {
                        Log.d("AndroidAspectj", "onClick() call")
                        joinPoint.proceed()
                    }
                    MustLogin::class.java -> {
                        Log.d("AndroidAspectj", "@MustLogin annotation method call")
                    }
                    else -> {
                        joinPoint.proceed()
                    }
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }

    override fun onClick(v: View?) {
        giveALike()
    }

    @MustLogin
    fun giveALike() {
        Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show()
    }
}