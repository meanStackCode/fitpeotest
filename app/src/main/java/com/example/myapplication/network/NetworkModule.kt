package com.example.myapplication.network

import android.app.Application
import android.content.Context
import com.example.myapplication.R
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

/**
 * Created by Deshraj Sharma on 10-03-2023.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        internetCheckInterceptor: InternetCheckInterceptor
    ): Retrofit {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(internetCheckInterceptor)
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Singleton
    @Provides
    fun providePhotoApi(retrofit: Retrofit): DataApi {
        return retrofit.create(DataApi::class.java)
    }

    @Provides
    @Singleton
    fun getNetwork(@ApplicationContext context: Context) = InternetCheckInterceptor(context)

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext
}