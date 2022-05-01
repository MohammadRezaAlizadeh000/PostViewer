package com.example.postviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.postviewer.R
import com.example.postviewer.utils.extensions.replaceTransaction
import com.example.postviewer.view.fragment.PostListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceTransaction(PostListFragment())
    }
}