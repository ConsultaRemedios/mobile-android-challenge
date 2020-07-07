package br.com.angelorobson.templatemvi.view

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
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
    private var isCartView = false

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
                    this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    changeStatusBarColor("#282828")
                }
                R.id.searchGameFragment -> {
                    this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    toolbar.visibility = GONE
                    changeStatusBarColor("#CCCCCC")
                }
                R.id.shoppingCardFragment -> {
                    this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    toolbar.visibility = VISIBLE
                    changeStatusBarColor("#0091ff")
                }
                R.id.gameDetailFragment -> {
                    toolbar.visibility = GONE
                    this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }

        }
    }


    private fun changeStatusBarColor(color: String) {
        window.statusBarColor = Color.parseColor(color)
    }

    override fun onDestroy() {
        activityService.onDestroy(this)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || return super.onSupportNavigateUp()
    }


}