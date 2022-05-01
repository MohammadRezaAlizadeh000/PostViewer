package com.example.postviewer.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.postviewer.di.AppDependenciesContainer
import com.example.postviewer.di.MyApplication
import com.example.postviewer.presentation.utils.extensions.inflater

abstract class BaseFragment : Fragment() {

    var dependenciesContainer: AppDependenciesContainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dependenciesContainer = (requireActivity().application as MyApplication).getDependenciesContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflater(getLayout()) ?: LayoutInflater.from(container?.context)
            .inflate(getLayout(), container, false)
    }

    abstract fun getLayout(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        viewActions()

        initObservers()
    }

    abstract fun initViews(view: View)

    abstract fun viewActions()

    abstract fun initObservers()

    override fun onDestroy() {
        super.onDestroy()
        dependenciesContainer = null
        System.gc()
    }
}