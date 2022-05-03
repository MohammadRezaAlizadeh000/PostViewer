package com.example.postviewer.presentation.utils.extensions

import android.util.Log
import com.example.postviewer.data.mapper.BasicMapper
import com.example.postviewer.presentation.utils.RequestErrorHandler
import com.example.postviewer.presentation.utils.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <T, R> Response<T>.toRequestState(mapper: BasicMapper<T, R>): RequestState<R> {
    return if (this.isSuccessful) {
        if (this.body() != null) {
//            RequestState.Success(data = this.body()!!)
            RequestState.Success(data = mapData(mapper, this.body()!!))
        } else {
            RequestState.Error(RequestErrorHandler.getErrorMessage(this.code()))
        }
    } else {
        RequestState.Error(RequestErrorHandler.getErrorMessage(this.code()))
    }
}


fun <T, R> Call<T>.toRequestState(result: (RequestState<R>) -> Unit, mapper: BasicMapper<T, R>) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            Log.d("REQUEST_TAG", "in getResponseCallback onResponse")
            if (response.body() != null) {
                result(RequestState.Success(mapData(mapper, response.body()!!)))
            } else {
                result(RequestState.Error(RequestErrorHandler.getErrorMessage(response.code())))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.d("REQUEST_TAG", "in getResponseCallback onFailure")
            result(RequestState.Error(message = "request Failed ====> ${t.message}"))
        }

    })


}

fun <T> Call<T>.toRequestState(result: (state: RequestState<T>) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            Log.d("REQUEST_TAG", "in getResponseCallback onResponse")
            if (response.body() != null) {
                result(RequestState.Success(data = response.body()!!))
            } else {
                result(RequestState.Error(RequestErrorHandler.getErrorMessage(response.code())))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.d("REQUEST_TAG", "in getResponseCallback onFailure")
            result(RequestState.Error(message = "request Failed ====> ${t.message}"))
        }

    })


}

fun <T, R> mapData(mapper: BasicMapper<T, R>, oldDAta: T): R {
    return mapper.map(oldDAta)
}
