package br.com.angelorobson.templatemvi.view

import android.content.Intent
import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.utils.FileUtils
import br.com.angelorobson.templatemvi.utils.TestUtils
import br.com.angelorobson.templatemvi.utils.withRecyclerView
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private val mockWebServer = MockWebServer()


    @Before
    fun setUp() {
        setupResponse()
        activityRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun initialViews() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.navigationHostFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigateToPullRequestScreen() {
        TestUtils.waitEspresso(2000)
        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_row_constraint_layout))
                .perform(click())

        onView(withId(R.id.pull_request_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testOpenWebBrowserAfterClickInPullRequest() {
        Intents.init()

        TestUtils.waitEspresso(2000)
        onView(withRecyclerView(R.id.repositories_recycler_view)
                .atPositionOnView(0, R.id.repository_row_constraint_layout))
                .perform(click())

        onView(withRecyclerView(R.id.pull_request_recycler_view)
                .atPositionOnView(0, R.id.pull_request_constraint_layout))
                .perform(click())

        intended(hasAction(Intent.ACTION_VIEW))
        intended(hasData(Uri.parse("https://github.com/MisterBooo/LeetCodeAnimation/pull/101")))
        Intents.release()
    }

    private fun setupResponse() {
        val endpointToRepositories = "/search/repositories?q=language%3AJava&sort=stars&page=1"
        val endpointToPullRequests = "/repos/CyC2018/CS-Notes/pulls"
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    endpointToRepositories -> return MockResponse()
                            .setResponseCode(200)
                            .setBody(FileUtils.getJson("json/repositories/repositories.json"))

                    endpointToPullRequests -> return MockResponse()
                            .setResponseCode(200)
                            .setBody(FileUtils.getJson("json/pullrequests/pulls.json"))
                }
                return MockResponse().setResponseCode(404)
            }
        }

        mockWebServer.dispatcher = dispatcher
        mockWebServer.start(8500)
    }
}