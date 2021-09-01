package com.android.weatherapplication

import org.koin.core.module.Module

/**
 * OpenFlight-based product.
 *
 * Used to declare all product-specific components.
 */
open class BaseProduct {

    /**
     * Dependency-injection modules, if any.
     */
    open val modules: List<Module> = emptyList()

}
