package teste.exemplo.com.gamecommerce.Utils


import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingRegistry

object WaitUtils {

    private val DEFAULT_WAIT_TIME = 3000

    private var idlingResource: IdlingResource? = null

    @JvmOverloads
    fun waitTime(waitingTime: Int = DEFAULT_WAIT_TIME) {
        cleanupWaitTime()

        idlingResource = ElapsedTimeIdlingResource(waitingTime.toLong())
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun cleanupWaitTime() {
        if (idlingResource == null) return
        IdlingRegistry.getInstance().unregister(idlingResource)
        idlingResource = null
    }
}
