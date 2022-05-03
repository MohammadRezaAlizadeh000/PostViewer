package com.example.postviewer.di

import com.example.postviewer.data.network.remotedatsource.RemoteDataSource
import com.example.postviewer.data.repository.Repository
import com.example.postviewer.domin.repository.RepositoryAccess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePostRepository(remoteDataSource: RemoteDataSource): RepositoryAccess {
        return Repository(remoteDataSource)
    }

}