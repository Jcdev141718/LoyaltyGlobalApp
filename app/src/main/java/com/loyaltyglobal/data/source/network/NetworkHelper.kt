package com.loyaltyglobal.data.source.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.loyaltyglobal.util.Constants
import retrofit2.Response

/**
 * Created by Abhin.
 */

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(
        message: String,
        data: T? = null
    ) : NetworkResult<T>(
        data,
        message
    )

    class Loading<T> : NetworkResult<T>()
}

abstract class BaseApiResponse(val context: Context) {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            return if (isInternetAvailable(context)) {
                val response = apiCall()
                when {
                    response.isSuccessful -> {
                        val body = response.body()
                        NetworkResult.Success(body)
                    }
                    response.code() == Constants.UNAUTHORISED -> {
                        error("Unauthorised request")
                    }
                    else -> error("${response.code()} ${response.message()}")
                }
            } else {
                error("No Internet available")
            }

        } catch (e: Exception) {
            return error(
                e.message
                    ?: e.toString()
            )
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}

fun <T>manageApiDataByState(results : NetworkResult<T>,listener : ApiResponseStates){
    when(results) {
        is NetworkResult.Loading -> {
            listener.onLoading()
        }
        is NetworkResult.Success -> {
            listener.onSuccess(results.data)
        }
        is NetworkResult.Error -> {
            listener.onError(results.message)
        }
    }
}

interface ApiResponseStates {
    fun onSuccess(responseData: Any?)
    fun onLoading()
    fun onError(message: String?)
}

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
        }
    } else {
        val activeNetwork: NetworkInfo? = connectivityManager?.activeNetworkInfo
        result = activeNetwork?.isConnectedOrConnecting == true
    }
    return result
}

