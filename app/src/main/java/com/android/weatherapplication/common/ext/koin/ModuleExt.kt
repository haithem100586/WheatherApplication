package com.android.weatherapplication.common.ext.koin

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.ScopeDSL

/**
 * Allows to easily declare a bean in koin, bound to the foreground scope.
 *
 * ```
 * module {
 *   foreground { scoped { MyBean() } }
 * }
 * ```
 */
fun Module.foreground(scopeDSL: ScopeDSL.() -> Unit) = scope(named("foreground"), scopeDSL)

