package com.example.myapplication.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.model.DataModel
import com.example.myapplication.repositories.ApiDataRepository
import com.example.myapplication.utils.NetworkResult
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Created by Deshraj Sharma on 14-03-2023.
 */
class ApiDataViewModelTest {

    private val testDispatcher= StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewModel: ApiDataViewModel

    @Mock
    private lateinit var repository: ApiDataRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ApiDataViewModel(repository)
    }

    @Test
    fun `test fetch api data success`() = runBlocking {
        // given
        val expectedData = listOf(
            DataModel("1", "2", "title1", "url1", "urlthumbnail1"),
            DataModel("2", "3", "title2", "url2", "urlthumbnail2")
        )

        // Stub the Retrofit service to return the success data
        `when`(repository.getApiData()).thenReturn(NetworkResult.Success(expectedData))

        // Call the ViewModel method to load the data
        viewModel.fetchData()

        // Verify that the ViewModel state is updated correctly
        viewModel.data.observeForever {state->
            assertNotNull(state)
            assertTrue(state is NetworkResult.Success)
            assertEquals(expectedData, (state as NetworkResult.Success).data)
        }
    }

    @Test
    fun `test fetch api data error`() = runBlocking {
        // Create a error response
        val response = Response.error<List<DataModel>>(404, ResponseBody.create(
            MediaType.parse("application/json"),
            "{\"message\":[\"Unauthorized\"]}"
        ))

        // Stub the Retrofit service to return the error response
        `when`(repository.getApiData()).thenReturn(NetworkResult.Error(response.message()))

        // Call the ViewModel method to load the data
        viewModel.fetchData()
        // Verify that the ViewModel state is updated correctly
        viewModel.data.observeForever { state ->
            assertNotNull(state)
            assertTrue(state is NetworkResult.Success)
            assertEquals(response.message(), (state as NetworkResult.Error).message)
        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}
