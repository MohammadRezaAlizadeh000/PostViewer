package com.example.postviewer.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.domin.usecase.PostListUseCase
import com.example.postviewer.presentation.utils.RequestState
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