package teste.exemplo.com.gamecommerce

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import teste.exemplo.com.gamecommerce.View.Main.MainActivity
import org.junit.Rule
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney
import teste.exemplo.com.gamecommerce.Utils.OrientationChangeAction
import teste.exemplo.com.gamecommerce.Utils.WaitUtils.cleanupWaitTime
import teste.exemplo.com.gamecommerce.Utils.WaitUtils.waitTime


@RunWith(AndroidJUnit4::class)
class CartScreenInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    private fun startActivityWithIntent() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun testAddAndRemoveQuantity() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        waitTime()

        var totalItemsBefore = Cart.totalItems

        onView(allOf(withId(R.id.add_quantity), isDisplayed()))
            .perform(click())

        check(totalItemsBefore + 1 == Cart.totalItems)

        waitTime()

        totalItemsBefore = Cart.totalItems

        onView(allOf(withId(R.id.remove_quantity), isDisplayed()))
            .perform(click())

        check(totalItemsBefore - 1 == Cart.totalItems)

        cleanupWaitTime()
    }

    @Test
    fun testRemoveItemFromCart() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.remove_quantity), isDisplayed()))
            .perform(click())

        onView(allOf(withId(R.id.item_image), isDisplayed()))

        cleanupWaitTime()
    }

    @Test
    fun testAddTwoDifferentGamesToCart() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.backArrow), isDisplayed()))
            .perform(click())

        onView(allOf(withId(R.id.backArrow), isDisplayed()))
            .perform(click())

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        waitTime()

        check(Cart.totalItems == 2)

        cleanupWaitTime()
    }


    @Test
    fun testFreeDeliveryTax() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        waitTime()

        while (Cart.totalGamesPrice <= 250.0) onView(allOf(withId(R.id.add_quantity), isDisplayed()))
            .perform(click())

        onView(allOf(withId(R.id.delivery_tax_value), isDisplayed()))
            .check(matches((withText(formatMoney(0.0)))))

        cleanupWaitTime()
    }


    @Test
    fun testScreenOrientationChangedToLandscape() {

        startActivityWithIntent()

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))

        waitTime()

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(click())

        waitTime()

        onView(allOf(withId(R.id.add_to_cart), isDisplayed()))
            .perform(click())

        val qtyBefore = Cart.totalItems

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape())

        waitTime()

        val qtyAfter = Cart.totalItems

        check(qtyBefore == qtyAfter)

        cleanupWaitTime()
    }

}
