package com.example.postviewer.data.network.remotedatsource

import com.example.postviewer.data.mapper.PostsListMapper
import com.example.postviewer.data.network.PostViewerAPIService
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.remotedatasource.RemoteDataSourceAccess
import com.example.postviewer.presentation.utils.RequestState
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val postViewerAPIService: PostViewerAPIService,
    private val helper: RemoteDataSourceHelper
): RemoteDataSourceAccess {

    override suspend fun getAllPost(): RequestState<List<SinglePostModel>> {
        return helper.getResponse({ postViewerAPIService.getAllPost() }, PostsListMapper)
    }

}