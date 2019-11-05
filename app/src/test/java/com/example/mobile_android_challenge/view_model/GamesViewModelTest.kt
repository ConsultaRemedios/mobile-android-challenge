package com.example.mobile_android_challenge.view_model

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobile_android_challenge.SchedulerProvider
import com.example.mobile_android_challenge.api.ApiClient
import com.example.mobile_android_challenge.api.GamesApi
import com.example.mobile_android_challenge.model.Game
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class GamesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiClient: ApiClient

    @Mock
    private val contextMock: Context? = null

    private val schedulerProvider: SchedulerProvider =
        object : SchedulerProvider {
            override fun mainThread(): Scheduler = Schedulers.trampoline()
            override fun io(): Scheduler = Schedulers.trampoline()
        }

    private lateinit var mainViewModel: GamesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = GamesViewModel(apiClient, schedulerProvider)
    }

    @Test
    fun `whenever fetch success with data it should result success with data`() {
        // Given
        val data = Game(11, "name", "platform", 12.0, "image")
        var apiKey: GamesApi
        Mockito.`when`(apiClient.games(GamesApi.API_KEY)).thenReturn(Observable.just(listOf(data)))

        // When
        mainViewModel.fethGames()

        // Then
        Assert.assertEquals(listOf(data), mainViewModel.data.value)
    }

    @Test
    fun `whenever fetch fails it should result in no data`() {
        // Given
        Mockito.`when`(apiClient.games(GamesApi.API_KEY)).thenReturn(Observable.error(Exception("dummyError")))

        // When
        mainViewModel.fethGames()

        // Then
        Assert.assertEquals(emptyList<Game>(), mainViewModel.data.value)
    }
}


