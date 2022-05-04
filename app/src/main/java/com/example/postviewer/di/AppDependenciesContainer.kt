package com.example.postviewer.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.postviewer.data.network.remotedatsource.RemoteDataSource
import com.example.postviewer.data.network.remotedatsource.RemoteDataSourceHelper
import com.example.postviewer.data.network.remotedatsource.RemoteDataSourceHelperImpl
import com.example.postviewer.data.repository.Repository
import com.example.postviewer.data.network.PostViewerAPIService
import com.example.postviewer.domin.remotedatesource.RemoteDataSourceAccess
import com.example.postviewer.domin.usecase.PostListUseCaseImpl
import com.example.postviewer.presentation.utils.BASE_URL
import com.example.postviewer.presentation.viewmodel.PostListViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppDependenciesContainer(
    private val applicationContext : Context
) {

    private var retrofit: Retrofit? = null
    private var gsonConvertorFactory: GsonConverterFactory? = null
    private var okHttpClient: OkHttpClient? = null
    private var remoteDataSourceHelperImpl: RemoteDataSourceHelper? = null
    private var apiService: PostViewerAPIService? = null


    private fun provideGsonConvertorFactory(): GsonConverterFactory {
        if (gsonConvertorFactory == null)
            gsonConvertorFactory = GsonConverterFactory.create()

        return gsonConvertorFactory!!
    }

    private fun provideOkHttpClient(): OkHttpClient {
        if (okHttpClient == null)
            okHttpClient = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
        return okHttpClient!!
    }

    private fun provideRetrofit(): Retrofit{
        if (retrofit == null)
            retrofit  = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(provideGsonConvertorFactory())
                .build()

        return retrofit!!
    }

    private fun getPostViewerAPIService(): PostViewerAPIService {
        if (apiService == null)
             apiService = provideRetrofit().create(PostViewerAPIService::class.java)
        return apiService!!
    }

    private fun remoteDataSourceHelper(): RemoteDataSourceHelper {
        if (remoteDataSourceHelperImpl == null)
            remoteDataSourceHelperImpl = RemoteDataSourceHelperImpl(applicationContext)

        return remoteDataSourceHelperImpl!!
    }

    private fun provideRemoteDataSource(): RemoteDataSourceAccess {
        return RemoteDataSource(getPostViewerAPIService(), remoteDataSourceHelper())
    }

    private fun provideRepository(): Repository {
        return Repository(provideRemoteDataSource())
    }

    fun providePostListViewModelFactory(): ViewModelProvider.Factory {
        return PostListViewModelFactory(PostListUseCaseImpl(provideRepository()))
    }

}