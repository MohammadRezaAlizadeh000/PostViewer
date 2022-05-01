package com.example.postviewer.domin.usecase

import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState

class PostListUseCaseImpl(
    private val repository: RepositoryAccess
): PostListUseCase {

    override suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return repository.getAllPosts()
    }

}