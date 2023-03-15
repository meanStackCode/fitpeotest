package com.example.myapplication.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Deshraj Sharma on 27-04-2022
 */
class InternetCheckInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!isNetworkConnected())
                throw NetworkConnectionException(
                    "No Internet Connection."
                )
            return chain.proceed(chain.request())
        } catch (e: Exception) {
            if (e is NetworkConnectionException) {
                throw NetworkConnectionException(
                    "No Internet Connection."
                )
            } else
                throw NetworkConnectionException("Server not reachable.")
        }
    }

    /**
     * Method called to check if the internet connection is active
     */
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        val result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }
}

class NetworkConnectionException(message: String) : IOException(message)
