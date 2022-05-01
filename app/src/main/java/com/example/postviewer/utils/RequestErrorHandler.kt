package com.example.postviewer.utils

object RequestErrorHandler {
    private val errorsMessages = mutableMapOf<Int, String>().apply {
        put(404, "درخواست اشتباه داده اید")
        put(500, "صرور مشکل دارد صبر کنید")
        put(300, "این لینک منقضی شده است")
    }

    fun getErrorMessage(errorCode: Int): String {
        return errorsMessages[errorCode] ?: "ارور مورد نظر تعریف نشده است"
    }
}