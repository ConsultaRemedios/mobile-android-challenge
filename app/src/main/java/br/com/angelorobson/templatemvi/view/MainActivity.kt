package br.com.angelorobson.templatemvi.view

import android.R.attr.*
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.utils.ActivityService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var activityService: ActivityService
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()

        activityService = applicationContext.component.activityService()
        activityService.onCreate(this)
        destinationListener()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.navigationHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun destinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    toolbar.visibility = GONE
                }
                R.id.searchGameFragment -> {
                    toolbar.visibility = GONE
                }
                else -> {
                    /* if (Build.VERSION.SDK_INT in 19..20) {
                         setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
                     }
                     if (Build.VERSION.SDK_INT >= 19) {
                         window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                     }
                     if (Build.VERSION.SDK_INT >= 21) {
                         setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                         window.statusBarColor = Color.TRANSPARENT
                     }*/

                    toolbar.visibility = VISIBLE
                }
            }

        }
    }

    override fun onDestroy() {
        activityService.onDestroy(this)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || return super.onSupportNavigateUp()
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

}