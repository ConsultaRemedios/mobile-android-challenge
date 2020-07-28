package com.games.ecommerce.main.ui.activity.gamedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.model.ShoppingGame
import com.games.ecommerce.main.data.repository.ShoppingRepository
import com.games.ecommerce.main.data.repository.StatusDB
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
class GameDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val shoppingRepository = mockk<ShoppingRepository>()
    private val isOnCartObserver: Observer<Boolean> = mockk(relaxed = true)

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
    fun `when checkGameStatus is called it should call repository and update isOnCart`() {
        val gameRepositoryResponse = GameRepositoryResponse(
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

        val shoppingGame = ShoppingGame(
            1,
            "Zelda",
            100.0,
            150.0,
            "https://image.com",
            2
        )
        val viewModel = GameDetailViewModel(shoppingRepository)

        viewModel.isOnCart.observeForever(isOnCartObserver)

        coEvery { shoppingRepository.getShoppingById(1) } returns shoppingGame

        viewModel.checkGameStatus(gameRepositoryResponse)

        coVerify { shoppingRepository.getShoppingById(1) }
        coVerify { isOnCartObserver.onChanged(true) }
    }

    @Test
    fun `when addOrRemoveOfCart is called and exist a shoppingGame isOnCart must be change to false`() {
        val gameRepositoryResponse = GameRepositoryResponse(
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

        val shoppingGame = ShoppingGame(
            1,
            "Zelda",
            40.0,
            50.0,
            "https://image.com",
            1
        )

        val viewModel = GameDetailViewModel(shoppingRepository)

        viewModel.isOnCart.observeForever(isOnCartObserver)

        coEvery { shoppingRepository.getShoppingById(1) } returns shoppingGame
        coEvery { shoppingRepository.removeShoppingGame(1) } returns StatusDB.SUCCESS

        viewModel.addOrRemoveOfCart(gameRepositoryResponse)

        coVerify { shoppingRepository.getShoppingById(1) }
        coVerify { shoppingRepository.removeShoppingGame(1) }
        coVerify { isOnCartObserver.onChanged(false) }
    }
}
