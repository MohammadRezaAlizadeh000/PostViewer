package com.example.postviewer.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.postviewer.R
import com.example.postviewer.presentation.utils.extensions.replaceTransaction
import com.example.postviewer.presentation.view.fragment.PostListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceTransaction(PostListFragment())
    }
}