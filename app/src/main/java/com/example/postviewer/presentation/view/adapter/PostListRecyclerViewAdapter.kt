package com.example.postviewer.presentation.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postviewer.R
import com.example.postviewer.domin.model.SinglePostModel
import com.example.postviewer.presentation.utils.extensions.inflater

class PostListRecyclerViewAdapter(
    private val clickListener: PostRecyclerViewOnClickListener
): RecyclerView.Adapter<PostListRecyclerViewAdapter.PostListHolder>() {

    private val dataList = mutableListOf<SinglePostModel>()
    fun submitData(newDataList: List<SinglePostModel>) {
        dataList.apply {
            clear()
            addAll(newDataList)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostListHolder {
        return PostListHolder(parent.inflater(R.layout.post_list_recycerview_row)).apply {
            itemView.setOnClickListener { clickListener.onPostClick(dataList[layoutPosition].postId) }
        }
    }

    override fun onBindViewHolder(
        holder: PostListHolder,
        position: Int
    ) {
        val model = dataList[position]
        with(holder) {
            postTitle.text = model.postTitle
            postContent.text = model.postBody
        }
    }

    override fun getItemCount(): Int = dataList.size

    class PostListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val postTitle: TextView = itemView.findViewById(R.id.postTitle)
        val postContent: TextView = itemView.findViewById(R.id.postContent)
    }
}