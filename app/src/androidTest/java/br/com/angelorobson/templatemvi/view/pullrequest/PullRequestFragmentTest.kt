package br.com.angelorobson.templatemvi.view.pullrequest

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
import br.com.angelorobson.templatemvi.utils.TestUtils.waitEspresso
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import br.com.angelorobson.templatemvi.view.component
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class PullRequestFragmentTest {

    private val mockWebServer = MockWebServer()
    var idlingResource: TestIdlingResource? = null
    private var scenario: FragmentScenario<PullRequestFragment>? = null

    @Before
    fun setUp() {
        val repository = RepositoryBuilder.Builder().oneRepository().build()
        val fragmentArgs = Bundle().apply {
            putParcelable("repository", repository)
            putString("title", repository.name)
        }

        val mockResponse = MockResponse()
                .setBody(FileUtils.getJson("json/pullrequests/pulls.json"))
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        scenario = launchFragmentInContainer<PullRequestFragment>(
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
    fun initialViews() {
        onView(withId(R.id.pull_request_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkItemsInRecyclerView() {
        onView(withRecyclerView(R.id.pull_request_recycler_view)
                .atPositionOnView(0, R.id.pull_request_title_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.pull_request_recycler_view)
                .atPositionOnView(0, R.id.pull_request_created_at_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.pull_request_recycler_view)
                .atPositionOnView(0, R.id.pull_request_description_text_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.pull_request_recycler_view)
                .atPositionOnView(0, R.id.pull_request_profile_image_view))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.pull_request_recycler_view)
                .atPositionOnView(0, R.id.pull_request_username_text_view))
                .check(matches(isDisplayed()))
    }


}