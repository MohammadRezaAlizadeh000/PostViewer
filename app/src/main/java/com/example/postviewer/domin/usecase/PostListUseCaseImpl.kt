package com.example.postviewer.domin.usecase

import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostListUseCaseImpl @Inject constructor(
    private val repository: RepositoryAccess
): PostListUseCase {

    override suspend fun getAllPosts(): RequestState<List<SinglePostModel>> {
        return repository.getAllPosts()
    }

    override suspend fun filterPostsList(filterExpression: String, postsList: List<SinglePostModel>): RequestState<List<SinglePostModel>> {
        return withContext(Dispatchers.Default) {
            val searchResult = mutableListOf<SinglePostModel>()
            postsList.forEach {
                if (it.postTitle.contains(filterExpression))
                    searchResult.add(it)
            }
            return@withContext if (searchResult.isEmpty())
                RequestState.Error("عبارت مورد نظر یافت نشد")
            else
                RequestState.Success(searchResult)
        }
    }

}