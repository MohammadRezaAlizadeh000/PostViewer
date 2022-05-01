package com.example.postviewer.data.network.remotedatsource


import com.example.postviewer.data.mapper.BasicMapper
import com.example.postviewer.presentation.utils.RequestState
import retrofit2.Response

interface RemoteDataSourceHelper {

    //    suspend fun <T> getCallback(requestFunction: () -> Call<T>): RequestState<T>
    suspend fun <T, R> getResponse(
        requestFunction: suspend () -> Response<T>,
        mapper: BasicMapper<T, R>
    ): RequestState<R>

//    suspend fun <T, R> getCallback(
//        requestFunction: suspend () -> Call<T>,
//        mapper: BasicMapper<T, R>
//    ): RequestState<R>

}