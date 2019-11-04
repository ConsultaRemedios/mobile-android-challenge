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


@RunWith(AndroidJUnit4::class)
class PurchaseSuccessScreenInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    private fun startActivityWithIntent() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun testGamePurchasedSuccessfully() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.finish_purchase), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.purchase_successful_message), isDisplayed()))

        cleanupWaitTime()
    }
}
