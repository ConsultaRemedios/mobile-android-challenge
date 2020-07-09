package br.com.angelorobson.templatemvi.view.gamedetail

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.di.TestComponent
import br.com.angelorobson.templatemvi.model.builders.SpotlightBuilder
import br.com.angelorobson.templatemvi.utils.FileUtils
import br.com.angelorobson.templatemvi.utils.TestIdlingResource
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameDetailFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<GameDetailFragment>? = null

    @Before
    fun setUp() {
        val spotligth = SpotlightBuilder.Builder()
                .id(1)
                .build()

        val fragmentArgs = Bundle().apply {
            putInt("id", spotligth.id)
        }

        val mockResponse = MockResponse()
                .setBody(FileUtils.getJson("json/game/game.json"))
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        scenario = launchFragmentInContainer<GameDetailFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar,
                fragmentArgs = fragmentArgs
        )

        scenario?.onFragment { fragment ->
            idlingResource =
                    ((fragment.activity!!.component as TestComponent).idlingResource() as TestIdlingResource)
            IdlingRegistry.getInstance().register(idlingResource!!.countingIdlingResource)
        }

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource!!.countingIdlingResource)
        mockWebServer.close()
    }

    @Test
    fun views() {
        onView(withId(R.id.game_detail_try_again_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.game_detail_image_view)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.game_Detail_add_item_card_floating_action_button)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_game_name_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_game_rating_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_rating_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_game_reviews_count_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_pricing_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_pricing_discount_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.game_detail_description)).check(matches(isDisplayed()))
    }


}