package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.DataModel
import com.example.myapplication.repositories.ApiDataRepository
import com.example.myapplication.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Deshraj Sharma on 11-03-2023.
 */
@HiltViewModel
class ApiDataViewModel @Inject constructor(private val repository: ApiDataRepository) :
    ViewModel() {

    private val apiData = MutableLiveData<NetworkResult<List<DataModel>>>()

    val data: LiveData<NetworkResult<List<DataModel>>>
        get() = apiData

    fun fetchData() {
        viewModelScope.launch {
            try {
                apiData.value = repository.getApiData()
            } catch (e: Exception) {
                apiData.value = NetworkResult.Error(e.message)
            }
        }
    }
}