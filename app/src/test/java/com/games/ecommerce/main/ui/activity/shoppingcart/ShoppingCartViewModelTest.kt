package com.games.ecommerce.main.ui.activity.shoppingcart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.games.ecommerce.main.data.model.ShoppingGame
import com.games.ecommerce.main.data.repository.GameRepositoryImpl
import com.games.ecommerce.main.data.repository.ShoppingRepository
import com.games.ecommerce.main.data.repository.StatusDB
import com.games.ecommerce.main.network.GameService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class ShoppingCartViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val service: GameService = mockk()
    private val shoppingRepository = mockk<ShoppingRepository>()

    private val shoppingGamesObserver: Observer<List<ShoppingGame>> = mockk(relaxed = true)
    private val checkoutSuccessObserver: Observer<Boolean> = mockk(relaxed = true)
    private val statusDBObserver: Observer<StatusDB> = mockk(relaxed = true)

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
    fun `when fetchData is called it should update shoppingGames`() {
        val shoppingGamesList = listOf(
            ShoppingGame(
                1,
                "Zelda",
                100.0,
                150.0,
                "https://image.com",
                2
            )
        )
        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        viewModel.shoppingGames.observeForever(shoppingGamesObserver)

        coEvery { shoppingRepository.getShoppingGames() } returns shoppingGamesList

        viewModel.fetchData()

        coVerify { shoppingGamesObserver.onChanged(shoppingGamesList) }
    }

    @Test
    fun `when checkout is called it should update checkoutSuccess to true`() {

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        viewModel.checkoutSuccess.observeForever(checkoutSuccessObserver)

        coEvery { service.checkout() } returns Unit

        viewModel.checkout()

        coVerify { checkoutSuccessObserver.onChanged(true) }
    }

    @Test
    fun `when clearCart is called it should call the repository`() {

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        coEvery { shoppingRepository.removeAllShoppingGames() } returns Unit

        viewModel.clearCart()

        coVerify { shoppingRepository.removeAllShoppingGames() }
    }

    @Test
    fun `when getPriceWithCharges is called it should check if has delivery charges`() {
        val shoppingGamesList = listOf(
            ShoppingGame(
                1,
                "Zelda",
                100.0,
                150.0,
                "https://image.com",
                2
            )
        )
        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        val result = viewModel.getPriceWithCharges(shoppingGamesList)

        Assert.assertEquals(220.0, result, 0.0)
    }

    @Test
    fun `when getPrice is called it should return the totalValue of products`() {
        val shoppingGamesList = listOf(
            ShoppingGame(
                1,
                "Zelda",
                100.0,
                150.0,
                "https://image.com",
                2
            )
        )
        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        val result = viewModel.getPrice(shoppingGamesList)

        Assert.assertEquals(200.0, result, 0.0)
    }

    @Test
    fun `when addOnCart is called then repository must be called and liveData updated`() {
        val shoppingGame = ShoppingGame(
            1,
            "Zelda",
            100.0,
            150.0,
            "https://image.com",
            2
        )
        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        viewModel.statusDB.observeForever(statusDBObserver)

        coEvery { shoppingRepository.saveShoppingGame(shoppingGame) } returns StatusDB.SUCCESS
        coEvery { shoppingRepository.getShoppingById(1) } returns shoppingGame

        viewModel.addOnCart(shoppingGame)

        coVerify { shoppingRepository.getShoppingById(1) }
        coVerify { statusDBObserver.onChanged(StatusDB.SUCCESS) }
        coVerify { shoppingRepository.saveShoppingGame(shoppingGame) }
    }

    @Test
    fun `when removeFromCart is called then repository must be called and liveData updated`() {
        val shoppingGame = ShoppingGame(
            1,
            "Zelda",
            100.0,
            150.0,
            "https://image.com",
            1
        )
        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        viewModel.statusDB.observeForever(statusDBObserver)

        coEvery { shoppingRepository.getShoppingById(1) } returns shoppingGame
        coEvery { shoppingRepository.removeShoppingGame(1) } returns StatusDB.SUCCESS

        viewModel.removeFromCart(shoppingGame)

        coVerify { shoppingRepository.getShoppingById(1) }
        coVerify { statusDBObserver.onChanged(StatusDB.SUCCESS) }
        coVerify { shoppingRepository.removeShoppingGame(1) }
    }

    @Test
    fun `when removeAllFromCart is called then repository must be called`() {
        val shoppingGame = ShoppingGame(
            1,
            "Zelda",
            100.0,
            150.0,
            "https://image.com",
            2
        )

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val viewModel = ShoppingCartViewModel(shoppingRepository, gameRepository)

        viewModel.statusDB.observeForever(statusDBObserver)

        coEvery { shoppingRepository.removeShoppingGame(1) } returns StatusDB.SUCCESS

        viewModel.removeAllFromCart(shoppingGame)

        coVerify { statusDBObserver.onChanged(StatusDB.SUCCESS) }
        coVerify { shoppingRepository.removeShoppingGame(1) }
    }
}
