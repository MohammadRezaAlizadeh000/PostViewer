package com.example.postviewer.di

import android.content.Context
import com.example.postviewer.data.network.PostViewerAPIService
import com.example.postviewer.data.network.remotedatsource.RemoteDataSource
import com.example.postviewer.data.network.remotedatsource.RemoteDataSourceHelper
import com.example.postviewer.data.network.remotedatsource.RemoteDataSourceHelperImpl
import com.example.postviewer.data.repository.Repository
import com.example.postviewer.domin.repository.RepositoryAccess
import com.example.postviewer.presentation.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideGsonConvertorFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun getPostViewerAPIService(retrofit: Retrofit): PostViewerAPIService {
        return retrofit.create(PostViewerAPIService::class.java)
    }

    @Provides
    fun provideRemoteDataSourceHelper(@ApplicationContext context: Context): RemoteDataSourceHelper {
        return RemoteDataSourceHelperImpl(context)
    }

}