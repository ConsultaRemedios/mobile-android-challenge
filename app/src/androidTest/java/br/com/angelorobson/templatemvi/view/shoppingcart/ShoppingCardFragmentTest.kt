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
import br.com.angelorobson.templatemvi.model.builders.ShoppingCartEntityBuilder
import br.com.angelorobson.templatemvi.utils.TestIdlingResource
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class ShoppingCardFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<ShoppingCardFragment>? = null

    @Before
    fun setUp() {
        val item = ShoppingCartEntityBuilder.Builder().oneShoppingCartEntity().build()
        scenario = launchFragmentInContainer<ShoppingCardFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )

        scenario?.onFragment { fragment ->
            idlingResource =
                    ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)

            (fragment.activity!!.applicationContext as AndroidTestApplication).shoppingCartItemsSubject.onNext(
                    listOf(item, item)
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
        onView(withId(R.id.shopping_cart_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.shopping_cart_games_count_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.shopping_card_total_price_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.shopping_card_total_discount_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.shopping_card_total_discount_currency_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.shopping_card_total_frete_currency_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.shopping_card_frete_text_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkItemsInRecyclerView() {
        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_game_image_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_game_title_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_remove_item_image_button))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_count_item_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_add_item_image_button))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_clear_cart))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_game_pricing_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.shopping_cart_recycler_view)
                .atPositionOnView(0, R.id.shopping_cart_game_discount_text_view))
                .check(matches(isDisplayed()))

    }


}