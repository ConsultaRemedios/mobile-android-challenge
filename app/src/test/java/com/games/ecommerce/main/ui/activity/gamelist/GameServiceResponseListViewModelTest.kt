package com.games.ecommerce.main.ui.activity.gamelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.games.ecommerce.main.data.db.dao.ShoppingGameDao
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.model.GameServiceResponse
import com.games.ecommerce.main.data.repository.GameRepositoryImpl
import com.games.ecommerce.main.data.repository.ShoppingRepositoryImpl
import com.games.ecommerce.main.network.GameService
import com.games.ecommerce.utils.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GameServiceResponseListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val shoppingGameDao: ShoppingGameDao = mockk()
    private val service: GameService = mockk()

    private val gamesFoundObserver: Observer<List<GameRepositoryResponse>> = mockk(relaxed = true)
    private val gameServiceResponseObserver: Observer<GameRepositoryResponse> =
        mockk(relaxed = true)


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchData is called it should update the livedatas`() {
        val gamesServiceResponse = listOf(serviceGameResponse())
        val gamesRepositoryResponse = listOf(repositoryGameResponse())

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)

        coEvery { service.getGames() } returns gamesServiceResponse
        coEvery { shoppingGameDao.getTotalAmount() } returns 1

        viewModel.fetchData()

        viewModel.games.test().assertValue(gamesRepositoryResponse, 1)
        viewModel.cartAmount.test().assertValue(1)
    }


    @Test
    fun `when searchGame is called it should update the livedata gamesFound`() {
        val gamesServiceResponse = listOf(serviceGameResponse())
        val gamesRepositoryResponse = listOf(repositoryGameResponse())

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)

        viewModel.gamesFound.observeForever(gamesFoundObserver)

        coEvery { service.searchGame("zel") } returns gamesServiceResponse

        viewModel.searchGame("zel")

        coVerify { gamesFoundObserver.onChanged(gamesRepositoryResponse) }
    }

    @Test
    fun `when search game by id update the livedata gameServiceResponse`() {
        val gamesServiceResponse = serviceGameResponse()
        val gamesRepositoryResponse = repositoryGameResponse()

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)

        viewModel.gameServiceResponse.observeForever(gameServiceResponseObserver)

        coEvery { service.gameById(1) } returns gamesServiceResponse

        viewModel.gameById(1)

        coVerify { gameServiceResponseObserver.onChanged(gamesRepositoryResponse) }
    }

    @Test
    fun `when checkFragmentVisibility is call with length 0, isSearchTextVisible should set with false`() {

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)

        viewModel.checkFragmentVisibility(0)
        viewModel.isSearchTextVisible.test().assertValue(false)
    }

    @Test
    fun `when checkFragmentVisibility is call with length 1, isSearchTextVisible should set with true`() {

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)

        viewModel.checkFragmentVisibility(1)
        viewModel.isSearchTextVisible.test().assertValue(true)
    }


    private fun serviceGameResponse(): GameServiceResponse {
        return GameServiceResponse(
            1,
            "Zelda",
            "http://zelda.com",
            50.0,
            10.0,
            "Descrição do zelda",
            4.5,
            4,
            "Nintendo",
            500
        )
    }

    private fun repositoryGameResponse(): GameRepositoryResponse {
        return GameRepositoryResponse(
            1,
            "Zelda",
            "http://zelda.com",
            40.0,
            50.0,
            "Descrição do zelda",
            4.5,
            4,
            "Nintendo",
            500
        )
    }

}
