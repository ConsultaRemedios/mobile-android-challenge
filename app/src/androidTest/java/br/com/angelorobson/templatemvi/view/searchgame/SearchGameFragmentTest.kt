package br.com.angelorobson.templatemvi.view.searchgame

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
import br.com.angelorobson.templatemvi.model.builders.RepositoryBuilder
import br.com.angelorobson.templatemvi.utils.FileUtils
import br.com.angelorobson.templatemvi.utils.TestIdlingResource
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchGameFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<SearchGameFragment>? = null

    @Before
    fun setUp() {
        val repository = RepositoryBuilder.Builder().oneRepository().build()
        val fragmentArgs = Bundle().apply {
            putParcelable("repository", repository)
            putString("title", repository.name)
        }

        val mockResponse = MockResponse()
                .setBody(FileUtils.getJson("json/game/game_search.json"))
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        scenario = launchFragmentInContainer<SearchGameFragment>(
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