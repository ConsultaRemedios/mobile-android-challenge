package teste.exemplo.com.gamecommerce.Utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup

import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

import org.hamcrest.Matcher

import androidx.test.espresso.matcher.ViewMatchers.isRoot


class OrientationChangeAction private constructor(private val orientation: Int) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return isRoot()
    }

    override fun getDescription(): String {
        return "change orientation to $orientation"
    }

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadUntilIdle()
        val activity = getActivity(view)
        activity!!.requestedOrientation = orientation

        val resumedActivities =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivities.isEmpty()) {
            throw RuntimeException("Could not change orientation")
        }
    }

    fun getActivity(view: View): Activity? {
        var activity: Activity? = null
        var context = view.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                activity = context
                break
            }
            context = context.baseContext
        }

        if (activity == null && view is ViewGroup) {
            val c = view.childCount
            var i = 0
            while (i < c && activity == null) {
                activity = getActivity(view.getChildAt(i))
                ++i
            }
        }

        return activity
    }

    companion object {

        fun orientationLandscape(): ViewAction {
            return OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        fun orientationPortrait(): ViewAction {
            return OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }
}