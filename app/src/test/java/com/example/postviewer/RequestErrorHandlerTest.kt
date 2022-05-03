package com.example.postviewer

import com.example.postviewer.presentation.utils.RequestErrorHandler
import org.junit.Assert.*
import org.junit.Test

class RequestErrorHandlerTest {

    @Test
    fun checkErrorHandlerIsCorrect() {
        assertNotNull(RequestErrorHandler.getErrorMessage(1))
        assertEquals(RequestErrorHandler.getErrorMessage(12), "ارور مورد نظر تعریف نشده است")
        assertEquals(RequestErrorHandler.getErrorMessage(404), "درخواست اشتباه داده اید")
    }
}