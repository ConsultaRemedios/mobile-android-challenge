package br.com.angelorobson.templatemvi.view.shoppingcart

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.com.angelorobson.templatemvi.AndroidTestApplication
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.di.TestComponent
import br.com.angelorobson.templatemvi.model.builders.ShoppingCartBuilder
import br.com.angelorobson.templatemvi.model.builders.ShoppingCartEntityBuilder
import br.com.angelorobson.templatemvi.utils.TestIdlingResource
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchGameFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<ShoppingCardFragment>? = null

    @Before
    fun setUp() {
        val item = ShoppingCartEntityBuilder.Builder().oneShoppingCartEntity().build()
        val list = listOf(item, item)

        scenario = launchFragmentInContainer<ShoppingCardFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar)

        scenario?.onFragment { fragment ->
            idlingResource =
                    ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)

            (fragment.activity!!.applicationContext as AndroidTestApplication).shoppingCartItemsSubject.onNext(
                    list
            )
        }

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource!!.countingIdlingResource)
        mockWebServer.close()
    }

    @Test
    fun views() {
        onView(withId(R.id.game_search_search_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.game_search_not_found_text_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_game_try_again_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_game_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkItemsInRecyclerView() {
        onView(withRecyclerView(R.id.search_game_recycler_view)
                .atPositionOnView(0, R.id.game_found_title_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.search_game_recycler_view)
                .atPositionOnView(0, R.id.game_found_game_pricing_discount_text_view))
                .check(matches(isDisplayed()))
    }


}