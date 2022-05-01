package com.example.postviewer.usecase

import com.example.postviewer.data.Repository
import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.utils.RequestState

class PostListUseCaseImpl(
    private val repository: Repository
): PostListUseCase {

    override suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return repository.getAllPosts()
    }

}