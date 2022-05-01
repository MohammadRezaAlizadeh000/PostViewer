package com.example.postviewer.domin.usecase

import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState

interface PostListUseCase {
    suspend fun getAllPosts(): RequestState<List<SinglePostModel>>
}