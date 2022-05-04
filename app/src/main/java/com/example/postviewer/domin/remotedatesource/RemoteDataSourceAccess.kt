package com.example.postviewer.domin.remotedatesource

import com.example.postviewer.data.mapper.PostsListMapper
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState

interface RemoteDataSourceAccess {

    suspend fun getAllPost(): RequestState<List<SinglePostModel>>

}