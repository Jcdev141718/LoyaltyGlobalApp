package com.loyaltyglobal.data.source.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.loyaltyglobal.R
import com.loyaltyglobal.util.Constants
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by Abhin.
 */

sealed class NetworkResult<T>(
    val responseData: T? = null, val message: String? = null, val statusCode: Int? = null
) {
    class Success<T>(data: T?, statusCode: Int? = null) :
        NetworkResult<T>(responseData = data, statusCode = statusCode)

    class Error<T>(message: String, statusCode: Int? = null) :
        NetworkResult<T>(message = message, statusCode = statusCode)

    class Loading<T> : NetworkResult<T>()
}

abstract class BaseApiResponse(val context: Context) {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        if (isInternetAvailable(context)) {
            val response = apiCall()
            when {
                response.isSuccessful -> {
                    val body = response.body()
                    return NetworkResult.Success(data = body, statusCode = response.code())
                }
                response.code() == Constants.UNAUTHORISED -> {
                    return NetworkResult.Error(
                        message = getErrorMessageFromResponse(
                            response.errorBody()?.string()
                        ), statusCode = response.code()
                    )
                }
                !response.isSuccessful -> {
                    return NetworkResult.Error(
                        message = getErrorMessageFromResponse(
                            response.errorBody()?.string()
                        ), statusCode = response.code()
                    )
                }
                else -> return NetworkResult.Error(
                    message = getErrorMessageFromResponse(
                        response.errorBody()?.string()
                    ), statusCode = response.code()
                )
            }
        } else {
            return NetworkResult.Error(context.getString(R.string.no_internet_available))
        }
    }

    private fun getErrorMessageFromResponse(errorBody: String?): String {
        return try {
            val jsonObj = JSONObject(errorBody!!)
            when {
                jsonObj.has("message") -> {
                    jsonObj.getString("message")
                }
                jsonObj.has("error") -> {
                    jsonObj.getString("error")
                }
                else -> {
                    ""
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    }
}

fun <T> manageApiDataByState(results: NetworkResult<T>, listener: ApiResponseStates) {
    when (results) {
        is NetworkResult.Loading -> {
            listener.onLoading()
        }
        is NetworkResult.Success -> {
            listener.onSuccess(responseData = results.responseData)
        }
        is NetworkResult.Error -> {
            results.statusCode?.let {
                listener.onError(codeData = it, message = results.message)
            }
        }
    }
}

interface ApiResponseStates {
    fun onSuccess(responseData: Any?)
    fun onLoading()
    fun onError(codeData: Int?, message: String?)
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

