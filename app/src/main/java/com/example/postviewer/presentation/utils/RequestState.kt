package com.example.postviewer.presentation.utils

sealed class RequestState<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T, message: String? = null): RequestState<T>(data = data, message = message)
    class Error<T>(message: String): RequestState<T>(message = message)
    class Loading<T>: RequestState<T>()
}
