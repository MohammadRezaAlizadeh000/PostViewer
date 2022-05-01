package com.example.postviewer.data.remotedatsource

import com.example.postviewer.data.mapper.PostsListMapper
import com.example.postviewer.data.network.PostViewerAPIService
import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.utils.RequestState

class RemoteDataSource(
    private val postViewerAPIService: PostViewerAPIService,
    private val helper: RemoteDataSourceHelper
) {

    suspend fun getAllPost(): RequestState<List<SinglePostModel>> {
        return helper.getResponse({ postViewerAPIService.getAllPost() }, PostsListMapper)
    }

}