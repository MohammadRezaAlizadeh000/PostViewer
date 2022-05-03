package com.example.postviewer.postusecase

import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.usecase.PostListUseCase
import com.example.postviewer.domin.usecase.PostListUseCaseImpl
import com.example.postviewer.postusecase.PostUseCaseFakeRepository
import com.example.postviewer.presentation.utils.RequestState
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class PostListUseCaseImplTest {

    private lateinit var useCase: PostListUseCase

    @Before
    fun init() {
        useCase = PostListUseCaseImpl(PostUseCaseFakeRepository())
    }

    @Test
    fun `getAllPosts function work properly` () = runBlocking {

        assertTrue("test success", useCase.getAllPosts() is RequestState.Success<List<SinglePostModel>>)

    }

    @Test
    fun `filterPostsList function work properly`() = runBlocking {
        val filterExpression = "laloo"
        val postsList = listOf(
            SinglePostModel(1, 1, "edsvo98", ""),
            SinglePostModel(1, 1, "dfrfebsde rtg tgse ", ""),
            SinglePostModel(1, 1, "we34", ""),
            SinglePostModel(1, 1, "lethd", ""),
            SinglePostModel(1, 1, "laloo", ""),
            SinglePostModel(1, 1, "dfvgrtd", ""),
        )

        assertTrue("test success", useCase.filterPostsList(filterExpression, postsList) is RequestState.Success<List<SinglePostModel>>)

    }

    @Test
    fun `filterPostsList function work not properly`() = runBlocking {
        val filterExpression = "laloo"
        val postsList = listOf(
            SinglePostModel(1, 1, "edsvo98", ""),
            SinglePostModel(1, 1, "dfrfebsde rtg tgse ", ""),
            SinglePostModel(1, 1, "we34", ""),
            SinglePostModel(1, 1, "lethd", ""),
            SinglePostModel(1, 1, "la1loo12", ""),
            SinglePostModel(1, 1, "dfvgrtd", ""),
        )

        assertTrue("test error", useCase.filterPostsList(filterExpression, postsList) is RequestState.Error<List<SinglePostModel>>)

    }

}