package com.example.postviewer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postviewer.domin.usecase.PostListUseCase


class PostListViewModelFactory(private val useCase: PostListUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostListViewModel(useCase) as T
    }

}