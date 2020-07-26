package java.games.ecommerce.main.ui.activity.gamelist

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
import java.games.ecommerce.main.data.db.dao.ShoppingGameDao
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.data.repository.GameRepositoryImpl
import java.games.ecommerce.main.data.repository.ShoppingRepositoryImpl
import java.games.ecommerce.main.network.GameService

@ExperimentalCoroutinesApi
class GameListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val shoppingGameDao: ShoppingGameDao = mockk()
    private val service: GameService = mockk()
    private val gamesObserver: Observer<List<Game>> = mockk(relaxed = true)


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
    fun `when fetchData is called it should call repository to return games data`()  {
        val games = listOf(
            Game(1, "Zelda", "http://zelda.com", 50.0, 10.0, "Descrição do zelda", 4.5, 4, "Nintendo", 500)
        )

        val gameRepository = GameRepositoryImpl(service, Dispatchers.IO)
        val shoppingRepository = ShoppingRepositoryImpl(shoppingGameDao)
        val viewModel = GameListViewModel(gameRepository, shoppingRepository)
        viewModel.games.observeForever(gamesObserver)

        coEvery { service.getGames() } returns games

        viewModel.fetchData()

        coVerify { gameRepository.getGames() }
    }
}