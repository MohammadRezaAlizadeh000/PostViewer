package com.example.postviewer.data.network.remotedatsource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.postviewer.data.mapper.BasicMapper
import com.example.postviewer.presentation.utils.RequestState
import com.example.postviewer.presentation.utils.extensions.toRequestState
import retrofit2.Response

class RemoteDataSourceHelperImpl(
    private val context: Context
) : RemoteDataSourceHelper {


//    override fun <T> getCallback(requestFunction: () -> Call<T>): RequestState<T> {
//        return RequestState<T> { emitter ->
//            try {
//                requestFunction().toAppState {
//                    emitter.onSuccess(it)
//                }
//            } catch (e: Exception) {
//                emitter.onError(e)
//            }
//        }.subscribeOn(Schedulers.io())
//    }

    override suspend fun <T, R> getResponse(
        requestFunction: suspend () -> Response<T>,
        mapper: BasicMapper<T, R>
    ): RequestState<R> {
        return try {
            if (checkInternetConnection()) {
                requestFunction().toRequestState(mapper)
            } else
                RequestState.Error("اینترنت خود را چک کنید")
        } catch (e: Exception) {
            Log.d("REQUEST_TAG", "in remote data source =====> ${e.message}")
            RequestState.Error(e.message.toString())
        }
    }

    /*override suspend fun <T, R> getCallback(
        requestFunction: suspend () -> Call<T>,
        mapper: BasicMapper<T, R>
    ): RequestState<R> {
        try {
            if (checkInternetConnection()) {
                requestFunction()
                    .toRequestState({
                        it
                    }, mapper)
            } else
                return RequestState.Error<R>("اینترنت خود را چک کنید")
        } catch (e: Exception) {
            return RequestState.Error<R>(e.message.toString())
        }
    }
*/
    private fun checkInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION") networkInfo.isConnected
        }
    }

}