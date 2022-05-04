package com.example.postviewer.data.repository

import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.remotedatesource.RemoteDataSourceAccess
import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.presentation.utils.RequestState

class Repository(
    private val remoteDataSource: RemoteDataSourceAccess
): RepositoryAccess {

    override suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return remoteDataSource.getAllPost()
    }
}