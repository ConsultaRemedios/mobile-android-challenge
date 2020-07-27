package com.games.ecommerce.main.ui.activity.gamelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import com.games.ecommerce.main.data.db.dao.ShoppingGameDao
import com.games.ecommerce.main.data.model.GameServiceResponse
import com.games.ecommerce.main.data.repository.GameRepositoryImpl
import com.games.ecommerce.main.data.repository.ShoppingRepositoryImpl
import com.games.ecommerce.main.network.GameService
import com.games.ecommerce.utils.test

@ExperimentalCoroutinesApi
class GameServiceResponseListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val shoppingGameDao: ShoppingGameDao = mockk()
    private val service: GameService = mockk()
    private val gamesObserver: Observer<List<GameServiceResponse>> = mockk(relaxed = true)
    private val cartAmountObserver: Observer<Int> = mockk(relaxed = true)
    private val gamesFoundObserver: Observer<List<GameServiceResponse>> = mockk(relaxed = true)


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
    fun `when fetchData is called it should update the livedatas`()  {
        val games = listOf(
            GameServiceResponse(1, "Zelda", "http://zelda.com", 50.0, 10.0, "Descrição do zelda", 4.5, 4, "Nintendo", 500)
        )

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)

        coEvery { service.getGames() } returns games
        coEvery { shoppingGameDao.getTotalAmount() } returns 1

        viewModel.fetchData()

        viewModel.games.test().assertValue(games, 1)
        viewModel.cartAmount.test().assertValue(1)
    }


    @Test
    fun `when searchGame is called it should call the repository and update the livedata gamesFound`()  {
        val games = listOf(
            GameServiceResponse(1, "Zelda", "http://zelda.com", 50.0, 10.0, "Descrição do zelda", 4.5, 4, "Nintendo", 500)
        )

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)
        viewModel.gamesFound.observeForever(gamesFoundObserver)

        coEvery { service.searchGame("zel") } returns games

        viewModel.searchGame("zel")

        coVerify { gamesFoundObserver.onChanged(games) }
    }

}
