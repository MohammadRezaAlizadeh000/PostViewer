package com.example.postviewer.data.mapper

import com.example.postviewer.data.model.SinglePostResponseModel
import com.example.postviewer.domin.model.SinglePostModel

object PostsListMapper:
    BasicMapper<List<SinglePostResponseModel>, List<SinglePostModel>> {
    override fun map(oldData: List<SinglePostResponseModel>): List<SinglePostModel> {
        return oldData.map {
            SinglePostModel(
                it.id,
                it.userId,
                it.title,
                it.body
            )
        }
    }
}