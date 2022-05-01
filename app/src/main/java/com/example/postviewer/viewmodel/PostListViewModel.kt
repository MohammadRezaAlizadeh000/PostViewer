package com.example.postviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.usecase.PostListUseCase
import com.example.postviewer.utils.RequestState
import kotlinx.coroutines.*

class PostListViewModel(private val useCase: PostListUseCase): ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.IO)
    val postLiveData: MutableLiveData<RequestState<List<SinglePostModel>>> = MutableLiveData()

    fun getAllPosts() = viewModelScope.launch {
        val result = useCase.getAllPosts()
        withContext(Dispatchers.Main) {
            postLiveData.value = result
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


}