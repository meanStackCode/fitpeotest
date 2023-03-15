package com.example.myapplication.network

import com.example.myapplication.model.DataModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Deshraj Sharma on 10-03-2023.
 */
interface DataApi {

    @GET("photos")
    suspend fun getDataFromApi(): Response<List<DataModel>>
}