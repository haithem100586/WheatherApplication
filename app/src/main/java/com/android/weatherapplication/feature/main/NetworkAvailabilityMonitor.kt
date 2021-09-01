package com.android.weatherapplication.feature.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

/**
 * LiveData listening to [ConnectivityManager] callbacks.
 */
class NetworkAvailabilityMonitor(context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkNames = mutableMapOf<Network, String?>()

    private val networkCallbackObject = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            networkNames[network] = network.name
            updateNetworkAvailability(connectivityManager.activeNetworkHasInternet())
        }

        override fun onLost(network: Network) {
            updateNetworkAvailability(connectivityManager.activeNetworkHasInternet())
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            updateNetworkAvailability(connectivityManager.activeNetworkHasInternet())
        }
    }

    override fun onActive() {
        super.onActive()
        updateNetworkAvailability(connectivityManager.activeNetworkHasInternet())
        connectivityManager.registerNetworkCallback(networkRequestBuilder(), networkCallbackObject)
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallbackObject)
        super.onInactive()
    }

    override fun setValue(value: Boolean?) {
        super.setValue(value)
    }

    private fun updateNetworkAvailability(isAvailable: Boolean) {
        if (isAvailable != value) {
            postValue(isAvailable)
        }
    }

    private fun networkRequestBuilder(): NetworkRequest {
        return NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .build()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Extension
    ///////////////////////////////////////////////////////////////////////////

    private fun ConnectivityManager.activeNetworkHasInternet(): Boolean {
        val activeNetworkCapabilities = getNetworkCapabilities(activeNetwork)
        return activeNetworkCapabilities != null
                && activeNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && activeNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private val Network?.name get() = connectivityManager.getLinkProperties(this)?.interfaceName
}
