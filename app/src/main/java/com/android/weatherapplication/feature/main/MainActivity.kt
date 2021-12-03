package com.android.weatherapplication.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.android.weatherapplication.common.BaseActivity
import com.android.weatherapplication.databinding.ActivityMainBinding

/**
 * Main activity that serves as an entry point to application.
 * It also makes sure the screen stays on.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bindView(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // every screen needs to stay on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}
