package com.example.postviewer

import com.example.postviewer.data.mapper.PostsListMapper
import com.example.postviewer.data.model.SinglePostResponseModel
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState
import com.example.postviewer.presentation.utils.extensions.mapData
import com.example.postviewer.presentation.utils.extensions.toRequestState
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Response

class NetworkExtensionsTest {

    @Test
    fun checkMapperFunction() {
        val oldData = listOf(
            SinglePostResponseModel(12, 12, "title", "body"),
            SinglePostResponseModel(32, 32, "title", "body"),
        )

        val result = listOf(
            SinglePostModel(12, 12, "title", "body"),
            SinglePostModel(32, 32, "title", "body"),
        )

        assertEquals(mapData(PostsListMapper, oldData), result)
    }

    @Test
    fun checkToRequestStateFunction() {
        val body = listOf(
            SinglePostResponseModel(12, 12, "title", "body"),
            SinglePostResponseModel(32, 32, "title", "body"),
        )
        val successResponse = Response.success(List(2) { body[it] })

        val failureResponse = Response.error<List<SinglePostResponseModel>>(555, ResponseBody.create(null, "چیکار میکنی ییییره"))

        assertTrue("success are the same", successResponse.toRequestState(PostsListMapper) is RequestState.Success)
        assertTrue("error are the same", failureResponse.toRequestState(PostsListMapper) is RequestState.Error)
    }

}

