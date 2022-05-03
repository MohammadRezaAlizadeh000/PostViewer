package com.example.postviewer.data.repository

import com.example.postviewer.data.network.remotedatsource.RemoteDataSource
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.presentation.utils.RequestState
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): RepositoryAccess {

    override suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return remoteDataSource.getAllPost()
    }
}