package com.example.postviewer.domin.repository

import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState

interface RepositoryAccess {

    suspend fun getAllPosts(): RequestState<List<SinglePostModel>>

}
