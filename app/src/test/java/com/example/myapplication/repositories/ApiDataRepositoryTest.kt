package com.example.myapplication.repositories

import com.example.myapplication.model.DataModel
import com.example.myapplication.network.DataApi
import com.example.myapplication.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Created by Deshraj Sharma on 15-03-2023.
 */
class ApiDataRepositoryTest {

    @Mock
    lateinit var api: DataApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getApiData_EmptyList() = runTest {
        `when`(api.getDataFromApi()).thenReturn(Response.success(emptyList()))

        val sut = ApiDataRepository(api)
        val result = sut.getApiData()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(0, result.data?.size)
    }

    @Test
    fun getApiData_WithList() = runTest {
        val apiData = listOf<DataModel>(
            DataModel("1", "1", "Title1", "Url1", "ThumbnailUrl1"),
            DataModel("1", "2", "Title2", "Url2", "ThumbnailUrl2"),
            DataModel("2", "3", "Title3", "Url3", "ThumbnailUrl3"),
            DataModel("2", "4", "Title4", "Url4", "ThumbnailUrl4"),
        )

        `when`(api.getDataFromApi()).thenReturn(Response.success(apiData))

        val sut = ApiDataRepository(api)
        val result = sut.getApiData()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(4, result.data?.size)
        Assert.assertEquals("Title3", result.data?.get(2)?.title)
    }

    @Test
    fun getApiData_Error() = runTest {
        `when`(api.getDataFromApi()).thenReturn(
            Response.error(
                401, ResponseBody.create(
                    MediaType.parse("application/json"),
                    "{\"message\":[\"Unauthorized\"]}"
                )
            )
        )

        val sut = ApiDataRepository(api)
        val result = sut.getApiData()
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something went wrong", result.message)
    }
}