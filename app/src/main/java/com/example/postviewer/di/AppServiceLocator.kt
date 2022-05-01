package com.example.postviewer.di

import android.content.Context
import com.example.postviewer.data.remotedatsource.RemoteDataSource
import com.example.postviewer.data.remotedatsource.RemoteDataSourceHelper
import com.example.postviewer.data.remotedatsource.RemoteDataSourceHelperImpl
import com.example.postviewer.data.Repository
import com.example.postviewer.data.network.PostViewerAPIService
import com.example.postviewer.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppServiceLocator(
    private val applicationContext : Context
) {

    private var retrofit: Retrofit? = null
    private var gsonConvertorFactory: GsonConverterFactory? = null
    private var okHttpClient: OkHttpClient? = null
    private var remoteDataSource: RemoteDataSource? = null
    private var remoteDataSourceHelperImpl: RemoteDataSourceHelper? = null
    private var repository: Repository? = null
//    private var applicationContext: Context? = null




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
        return provideRetrofit().create(PostViewerAPIService::class.java)
    }

    private fun remoteDataSourceHelper(): RemoteDataSourceHelper {
        if (remoteDataSourceHelperImpl == null)
            remoteDataSourceHelperImpl = RemoteDataSourceHelperImpl(applicationContext)

        return remoteDataSourceHelperImpl!!
    }

    private fun provideRemoteDataSource(): RemoteDataSource {
        if (remoteDataSource == null)
            remoteDataSource = RemoteDataSource(getPostViewerAPIService(), remoteDataSourceHelper())
        return remoteDataSource!!
    }

    fun provideRepository(): Repository {
        if (repository == null)
            repository = Repository(provideRemoteDataSource())
        return repository!!
    }

}