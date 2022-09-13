package com.example.navidemo.di.module

import com.example.navidemo.constants.Constants
import com.example.navidemo.network.interfaces.GithubAPI
import com.example.navidemo.repository.GithubApiRepo
import com.example.navidemo.repository.implementation.GithubApiImpl
import com.example.navidemo.viewmodel.GetPullRequestViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val connectTimeOut: Long = 30
    private const val readTimeOut: Long = 15
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder().build()
                chain.proceed(newRequest)
            }
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(okHttpClient: OkHttpClient): GithubAPI {
         val moshi = Moshi.Builder()
             .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build().create(GithubAPI::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideGithubApi(retrofit: Retrofit): GithubAPI {
//        return retrofit.
//    }

    @Provides
    @Singleton
    fun providesGithubRepoImpl(githubAPI: GithubAPI): GithubApiRepo {
        return GithubApiImpl(githubAPI)
    }
}