package com.android.wheatherapplication.feature.main

import android.view.LayoutInflater
import com.android.wheatherapplication.common.BaseActivity
import com.android.wheatherapplication.databinding.ActivityMainBinding

/**
 * Main activity that serves as an entry point to application.
 * It also makes sure the screen stays on.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bindView(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}
