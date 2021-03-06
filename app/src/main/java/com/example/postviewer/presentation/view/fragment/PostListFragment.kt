package com.example.postviewer.presentation.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.postviewer.presentation.viewmodel.PostListViewModel
import com.example.postviewer.R
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.REQUEST_CALLED
import com.example.postviewer.presentation.utils.RequestState
import com.example.postviewer.presentation.utils.SEARCH_CALLED
import com.example.postviewer.presentation.utils.extensions.postsRecyclerViewDialog
import com.example.postviewer.presentation.utils.extensions.toast
import com.example.postviewer.presentation.view.adapter.PostListRecyclerViewAdapter
import com.example.postviewer.presentation.view.adapter.PostRecyclerViewOnClickListener
import kotlinx.coroutines.*

class PostListFragment : BaseFragment() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var searchBox: EditText
    private lateinit var viewModel: PostListViewModel
    private val postsDataList = mutableListOf<SinglePostModel>()
    private val postsSearchedList = mutableListOf<SinglePostModel>()

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
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        swipeRefresh = view.findViewById(R.id.postListFragmentSwipeRefresh)
        searchBox = view.findViewById(R.id.postSearchBox)
    }

    override fun viewActions() {
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            hideSearchBox()
            getAllPosts()
        }

        postRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) hideSearchBox()
                else showSearchBox()

            }
        })

        getAllPosts()

        searchBoxAction()
    }

    private fun searchBoxAction() {

        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(content: CharSequence?, start: Int, count: Int, after: Int) {
                if (content!!.length > 2) {
                    Log.d("SEARCH_BOX_TAG", content.toString())
                    viewModel.filterPostsList(content.toString(), postsSearchedList)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isEmpty()) {
                    Log.d("SEARCH_BOX_TAG", "afterTextChanged = s is $s")
                    setDatToRecyclerView(postsDataList)
                }
            }

        })
    }

    private fun getAllPosts() {
        viewModel.getAllPosts()
    }

    private fun hideSearchBox() {
        searchBox.visibility = View.GONE
    }

    private fun showSearchBox() {
        searchBox.visibility = View.VISIBLE
    }

    private fun clearSearchBoxContent() {
        searchBox.setText("")
    }

    private fun setDatToRecyclerView(data: List<SinglePostModel>) {
        postRecyclerView.adapter = postListRecyclerViewAdapter
        postListRecyclerViewAdapter.submitData(data)
    }

    override fun initObservers() {
        viewModel.postLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RequestState.Error -> {
                    swipeRefresh.isRefreshing = false
                    toast(response.message.toString())
                }
                is RequestState.Loading -> toast("???? ?????? ?????????????? ??????????????...")
                is RequestState.Success -> {
                    Log.d("REQUEST_TAG", "in fragment success")
                    showSearchBox()
                    swipeRefresh.isRefreshing = false
                    if (response.message != null && response.message == REQUEST_CALLED) {
                        postsDataList.addAll(response.data!!)
                        postsSearchedList.addAll(response.data)
                        clearSearchBoxContent()
                    } else if (response.message == SEARCH_CALLED)
                        postsSearchedList.addAll(response.data!!)
                    setDatToRecyclerView(response.data!!)
                }
            }
        }
    }
}