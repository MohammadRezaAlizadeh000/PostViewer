package com.example.postviewer.usecase

import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.utils.RequestState

interface PostListUseCase {
    suspend fun getAllPosts(): RequestState<List<SinglePostModel>>
}