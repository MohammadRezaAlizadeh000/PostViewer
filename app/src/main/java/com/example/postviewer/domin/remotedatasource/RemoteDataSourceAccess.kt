package com.example.postviewer.domin.remotedatasource

import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState

interface RemoteDataSourceAccess {

    suspend fun getAllPost(): RequestState<List<SinglePostModel>>

}