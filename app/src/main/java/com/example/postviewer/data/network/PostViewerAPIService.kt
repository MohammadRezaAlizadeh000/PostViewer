package com.example.postviewer.data.network

import com.example.postviewer.data.model.SinglePostResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PostViewerAPIService {

    @GET("posts/")
    suspend fun getAllPost(): Response<List<SinglePostResponseModel>>
}