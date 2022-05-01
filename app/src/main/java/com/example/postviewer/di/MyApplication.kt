package com.example.postviewer.di

import android.app.Application

class MyApplication: Application() {
    private var dependenciesContainer: AppDependenciesContainer? = null

    fun getDependenciesContainer(): AppDependenciesContainer {
        if (dependenciesContainer == null)
            dependenciesContainer = AppDependenciesContainer(applicationContext)

        return dependenciesContainer!!
    }
}