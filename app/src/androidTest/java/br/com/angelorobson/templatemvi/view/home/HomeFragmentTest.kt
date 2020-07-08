package br.com.angelorobson.templatemvi.view.home

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.di.TestComponent
import br.com.angelorobson.templatemvi.utils.FileUtils
import br.com.angelorobson.templatemvi.utils.TestIdlingResource
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<HomeFragment>? = null

    @Before
    fun setup() {
        setupDispatcher()

        scenario = launchFragmentInContainer<HomeFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
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

    private fun setupDispatcher() {
        val endpointToBanners = "/banners"
        val endpointToSpotlights = "/spotlight"

        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    endpointToBanners -> return MockResponse()
                            .setResponseCode(200)
                            .setBody(FileUtils.getJson("json/banners/banners.json"))

                    endpointToSpotlights -> return MockResponse()
                            .setResponseCode(200)
                            .setBody(FileUtils.getJson("json/spotligths/spotligths.json"))
                }
                return MockResponse().setResponseCode(404)
            }
        }

        mockWebServer.dispatcher = dispatcher
        mockWebServer.start(8500)
    }

    @Test
    fun views() {
        onView(withId(R.id.home_spotlights_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.home_try_again_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.home_cart_floating_action_button)).check(matches(isDisplayed()))
        onView(withId(R.id.home_carousel)).check(matches(isDisplayed()))
    }

    @Test
    fun checkItemsInRecyclerView() {
        onView(withRecyclerView(R.id.home_spotlights_recycler_view)
                .atPositionOnView(0, R.id.game_item_image_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.home_spotlights_recycler_view)
                .atPositionOnView(0, R.id.game_item_company_name_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.home_spotlights_recycler_view)
                .atPositionOnView(0, R.id.game_item_game_name_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.home_spotlights_recycler_view)
                .atPositionOnView(0, R.id.game_item_game_pricing_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.home_spotlights_recycler_view)
                .atPositionOnView(0, R.id.game_item_game_pricing_discount_text_view))
                .check(matches(isDisplayed()))
    }

}