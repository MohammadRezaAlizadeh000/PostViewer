package com.example.postviewer

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.CoroutineScope

class PostListFragment: BaseFragment() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int = R.layout.post_list_fragment

    override fun initViews(view: View) {
        postRecyclerView = view.findViewById(R.id.postsRecyclerView)
        swipeRefresh = view.findViewById(R.id.postListFragmentSwipeRefresh)
    }

    override fun viewActions() {
        TODO("Not yet implemented")


    }

    override fun initObservers() {
        TODO("Not yet implemented")
    }
}