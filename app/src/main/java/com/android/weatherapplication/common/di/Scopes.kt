package com.android.weatherapplication.common.di

import com.android.weatherapplication.common.BaseApplication

/**
 * Foreground scope: created/destroyed based on first/last activity creation/destruction.
 *
 * @see [DestructionLifeCycleOwner].
 */
val foreground get() = BaseApplication.foreground
