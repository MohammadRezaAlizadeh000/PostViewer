package com.example.postviewer.data

import com.example.postviewer.data.remotedatsource.RemoteDataSource
import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.utils.RequestState

class Repository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return remoteDataSource.getAllPost()
    }
}