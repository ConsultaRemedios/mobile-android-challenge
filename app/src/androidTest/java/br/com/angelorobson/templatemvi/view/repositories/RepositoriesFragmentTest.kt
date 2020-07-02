package br.com.angelorobson.templatemvi.view.repositories

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
import br.com.angelorobson.templatemvi.utils.TestUtils.waitEspresso
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class RepositoriesFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<RepositoriesFragment>? = null

    @Before
    fun setUp() {
        val mockResponse = MockResponse()
                .setBody(FileUtils.getJson("json/repositories/repositories.json"))
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        scenario = launchFragmentInContainer<RepositoriesFragment>(
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

    @Test
    fun initialViews() {
        onView(withId(R.id.repositories_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.repository_try_again_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun checkItemsInRecyclerView() {
        waitEspresso(2000)
        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_title_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_sub_title_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_forks_count_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_stargaze_count_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_stargaze_count_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_profile_image_image_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_username_text_view))
                .check(matches(isDisplayed()))
    }

}