package com.example.postviewer.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.postviewer.presentation.viewmodel.PostListViewModel
import com.example.postviewer.R
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.RequestState
import com.example.postviewer.presentation.utils.extensions.postsRecyclerViewDialog
import com.example.postviewer.presentation.utils.extensions.toast
import com.example.postviewer.presentation.view.adapter.PostListRecyclerViewAdapter
import com.example.postviewer.presentation.view.adapter.PostRecyclerViewOnClickListener
import kotlinx.coroutines.*

class PostListFragment : BaseFragment() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var viewModel: PostListViewModel

    private val postListRecyclerViewAdapter: PostListRecyclerViewAdapter by lazy {
        PostListRecyclerViewAdapter(object : PostRecyclerViewOnClickListener {
            override fun onPostClick(postId: Int) {
                postsRecyclerViewDialog(postId.toString())
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            dependenciesContainer?.providePostListViewModelFactory()!!
        ).get(PostListViewModel::class.java)
    }

    override fun getLayout(): Int = R.layout.post_list_fragment

    override fun initViews(view: View) {
        postRecyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView).apply {
            adapter = postListRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        swipeRefresh = view.findViewById(R.id.postListFragmentSwipeRefresh)
    }

    override fun viewActions() {
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            viewModel.getAllPosts()
        }


        viewModel.getAllPosts()
    }

    private fun setDatToRecyclerView(data: List<SinglePostModel>) {
        postListRecyclerViewAdapter.submitData(data)
    }

    override fun initObservers() {
        viewModel.postLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is RequestState.Error -> {
                    swipeRefresh.isRefreshing = false
                    toast(response.message.toString())
                }
                is RequestState.Loading -> toast("در حال بارگیری اطلاعات...")
                is RequestState.Success -> {
                    Log.d("REQUEST_TAG", "in fragment success")
                    CoroutineScope(Dispatchers.Main).launch {
                        swipeRefresh.isRefreshing = false
                        delay(200)
                        // in the below function recyclerview call notifyDataSetChange() and this is
                        // heavy function on Main Thread.
                        // in other side swipe refresh layout is refreshing and we say it above to
                        // disable it. this action work on Main Thread.
                        // so when this to action happen at the same time it goes to lag for a second
                        // or mil second. so we use a delay on Main Thread using coroutine to prevent
                        // lag on ui
                        setDatToRecyclerView(response.data!!)
                    }
                }
            }
        }
    }
}