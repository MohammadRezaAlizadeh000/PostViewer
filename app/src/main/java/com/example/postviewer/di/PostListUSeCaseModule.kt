package com.example.postviewer.di

import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.domin.usecase.PostListUseCase
import com.example.postviewer.domin.usecase.PostListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PostListUSeCaseModule {

    @Provides
    fun providePostListUseCase(repository: RepositoryAccess): PostListUseCase {
        return PostListUseCaseImpl(repository)
    }

}