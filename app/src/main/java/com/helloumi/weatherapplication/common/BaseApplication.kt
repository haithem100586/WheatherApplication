package com.helloumi.weatherapplication.common

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.helloumi.weatherapplication.BaseProduct
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

/**
 * Base class to be extended by applications.
 *
 * Handles Koin dependency injection init, also provides ways to react to the app going
 * background / foreground (ie. callbacks + a koin scope).
 */
abstract class BaseApplication : Application() {

    /**
     * Product corresponding to this android application.
     */
    private val product by lazy {
        Class.forName("$packageName.Product")
            .kotlin
            .objectInstance as BaseProduct
    }

    /**
     * Koin dependency injection modules to be used for injection.
     *
     * e.g.
     * ```
     * override val modules = listOf(module {
     *   foreground { scoped { MyForegroundBean() } }
     *   viewModel { MyViewModel() }
     *   single { MySingleton() }
     * }
     * ```
     */
    abstract val modules: List<Module>

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLifecycle()
    }

    /**
     * Called before app goes to foreground (ie. no activity was present, one gets created).
     */
    open fun onPreForeground() {}

    /**
     * Called when app goes to foreground (ie. no activity was visible, one becomes visible).
     */
    open fun onForeground() {}

    /**
     * Called when app goes to background (ie. an activity was visible, it becomes hidden and no other activity will
     * become visible just afterwards).
     */
    open fun onBackground() {}

    /**
     * Called when app has gone to background (ie. an activity was present, it gets destroyed and no other activity gets
     * created just afterwards).
     */
    open fun onPostBackground() {}

    /**
     * Init koin dependency injection.
     */
    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(modules)
        }

        // load product modules
        getKoin().loadModules(product.modules)

        foreground = createForegroundScope()
    }

    /**
     * Detects app foreground / background changes.
     */
    private fun initLifecycle() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                onForeground()
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                onBackground()
            }
        })

        DestructionLifeCycleOwner.init(this)
        DestructionLifeCycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                onPreForeground()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                onPostBackground()

                // renew scope for foreground-bound injections, so we get fresh instances
                foreground.close()
                foreground = createForegroundScope()
            }
        })
    }

    private fun createForegroundScope() =
        getKoin().createScope("foregroundSession", named(FOREGROUND_SCOPE_NAME))

    companion object {
        /**
         * Name of the koin scope for foreground-bound components.
         */
        const val FOREGROUND_SCOPE_NAME = "foreground"

        /**
         * Koin scope that starts when the first activity gets created and stops shortly after the last one gets
         * destroyed.
         */
        lateinit var foreground: Scope
    }
}
