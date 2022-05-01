package com.example.postviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postviewer.usecase.PostListUseCase


class PostListViewModelFactory(private val useCase: PostListUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostListViewModel(useCase) as T
    }

}