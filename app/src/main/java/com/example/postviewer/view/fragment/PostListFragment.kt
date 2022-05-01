package com.example.postviewer.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.postviewer.viewmodel.PostListViewModel
import com.example.postviewer.viewmodel.PostListViewModelFactory
import com.example.postviewer.R
import com.example.postviewer.di.AppServiceLocator
import com.example.postviewer.model.SinglePostModel
import com.example.postviewer.usecase.PostListUseCaseImpl
import com.example.postviewer.utils.RequestState
import com.example.postviewer.utils.extensions.postsRecyclerViewDialog
import com.example.postviewer.utils.extensions.toast
import com.example.postviewer.view.adapter.PostListRecyclerViewAdapter
import com.example.postviewer.view.adapter.PostRecyclerViewOnClickListener

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
            PostListViewModelFactory(PostListUseCaseImpl(AppServiceLocator(requireContext()).provideRepository()))
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
                    toast(response.message.toString())
                    swipeRefresh.isRefreshing = false
                }
                is RequestState.Loading -> toast("در حال بارگیری اطلاعات...")
                is RequestState.Success -> {
                    setDatToRecyclerView(response.data!!)
                    swipeRefresh.isRefreshing = false
                }
            }
        }
    }
}