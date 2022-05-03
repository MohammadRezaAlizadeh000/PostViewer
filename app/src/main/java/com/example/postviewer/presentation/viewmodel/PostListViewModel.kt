package com.example.postviewer.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.usecase.PostListUseCase
import com.example.postviewer.presentation.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val useCase: PostListUseCase): ViewModel() {

    val postLiveData: MutableLiveData<RequestState<List<SinglePostModel>>> = MutableLiveData()

    fun getAllPosts() = viewModelScope .launch {
        val result = useCase.getAllPosts()
        withContext(Dispatchers.Main) {
            postLiveData.value = result
        }
    }

    fun filterPostsList(filterExpression: String, postsList: List<SinglePostModel>) = viewModelScope.launch {
        val result = useCase.filterPostsList(filterExpression, postsList)
        withContext(Dispatchers.Main) {
            postLiveData.value = result
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}