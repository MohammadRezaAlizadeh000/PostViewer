package com.example.postviewer.data.mapper

interface BasicMapper<T, R> {
    fun map(oldData: T): R
}