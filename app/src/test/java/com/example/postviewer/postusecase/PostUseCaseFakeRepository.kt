package com.example.postviewer.postusecase

import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.presentation.utils.RequestState

class PostUseCaseFakeRepository: RepositoryAccess {

    override suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return RequestState.Success(listOf(
            SinglePostModel(1, 1, "laloo", ""),
        ))
    }
}