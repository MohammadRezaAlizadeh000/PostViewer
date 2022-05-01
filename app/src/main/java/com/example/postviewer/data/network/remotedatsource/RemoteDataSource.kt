package com.example.postviewer.data.network.remotedatsource

import com.example.postviewer.data.mapper.PostsListMapper
import com.example.postviewer.data.network.PostViewerAPIService
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState

class RemoteDataSource(
    private val postViewerAPIService: PostViewerAPIService,
    private val helper: RemoteDataSourceHelper
) {

    suspend fun getAllPost(): RequestState<List<SinglePostModel>> {
        return helper.getResponse({ postViewerAPIService.getAllPost() }, PostsListMapper)
    }

}