package com.example.postviewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.usecase.PostListUseCase
import com.example.postviewer.utils.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PostListViewModel(private val useCase: PostListUseCase): ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.IO)
    val postLiveData: MutableLiveData<RequestState<List<SinglePostModel>>> = MutableLiveData()

    fun getAllPosts() = viewModelScope.launch {
        useCase.getAllPosts()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


}