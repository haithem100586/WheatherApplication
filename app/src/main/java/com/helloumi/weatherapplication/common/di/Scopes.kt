package com.helloumi.weatherapplication.common.di

import com.helloumi.weatherapplication.common.BaseApplication

/**
 * Foreground scope: created/destroyed based on first/last activity creation/destruction.
 *
 * @see [DestructionLifeCycleOwner].
 */
val foreground get() = BaseApplication.foreground
