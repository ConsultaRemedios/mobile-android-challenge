package teste.exemplo.com.gamecommerce

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import teste.exemplo.com.gamecommerce.View.Main.MainActivity
import org.junit.Rule
import android.content.Intent
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Utils.WaitUtils.cleanupWaitTime
import teste.exemplo.com.gamecommerce.Utils.WaitUtils.waitTime
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.action.ViewActions.typeText
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Utils.OrientationChangeAction.Companion.orientationLandscape


@RunWith(AndroidJUnit4::class)
class HomeScreenInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    private fun startActivityWithIntent() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun testHomeScreenDisplaysGames() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))

        cleanupWaitTime()
    }

    @Test
    fun testGameSelect() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.scrollView2), isDisplayed()))

        cleanupWaitTime()
    }


    @Test
    fun testScreenOrientationChangedToLandscape() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))

        onView(isRoot()).perform(orientationLandscape())

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(click())

        cleanupWaitTime()
    }

}
