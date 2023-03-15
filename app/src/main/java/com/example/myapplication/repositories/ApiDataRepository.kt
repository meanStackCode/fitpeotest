package com.example.myapplication.repositories

import com.example.myapplication.model.DataModel
import com.example.myapplication.network.DataApi
import com.example.myapplication.utils.NetworkResult
import javax.inject.Inject

/**
 * Created by Deshraj Sharma on 11-03-2023.
 */
class ApiDataRepository @Inject constructor(private val api: DataApi) {

    suspend fun getApiData(): NetworkResult<List<DataModel>> {
        val response = api.getDataFromApi()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null)
                NetworkResult.Success(responseBody)
            else NetworkResult.Error("Something went wrong")
        } else
            NetworkResult.Error("Something went wrong")
    }
}