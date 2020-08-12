package br.com.weslleymaciel.gamesecommerce.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.Banner
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.common.utils.CartHelper
import br.com.weslleymaciel.gamesecommerce.view.adapters.BannerAdapter
import br.com.weslleymaciel.gamesecommerce.view.adapters.SpotlightAdapter
import br.com.weslleymaciel.gamesecommerce.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity() {
    private val viewModel = GamesViewModel()

    private val observerBanner = Observer<List<Banner>?> {
        it?.let {
            loadBanners(it)
            cvBannerLoad.visibility = View.GONE
            vpBanner.visibility = View.VISIBLE
        }
    }

    private val observerSpotlight = Observer<List<Game>?> {
        it?.let {
            loadGames(it)
            cvSpotlightLoad.visibility = View.GONE
            rvSpotlight.visibility = View.VISIBLE
        }
    }

    fun loadBanners(banners: List<Banner>){
        val fragments = mutableListOf<Fragment>()

        for(banner in banners){
            fragments.add(BannerFragment(banner))
        }

        configureViewPager(fragments)
    }

    fun loadGames(games: List<Game>){
        configureSpotlight(games)
    }

    private fun configureSpotlight(games: List<Game>){
        rvSpotlight.layoutManager = GridLayoutManager(this, 2)
        rvSpotlight.adapter = SpotlightAdapter(games) {
            run {
                startActivity<GameDetailsActivity>("ID" to it.toInt())
            }
        }
    }

    private fun configureViewPager(fragments: List<Fragment>) {
        vpBanner.adapter = BannerAdapter(supportFragmentManager, fragments)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if (vpBanner.currentItem < fragments.size - 1 ){
                    vpBanner.currentItem += 1
                }else{
                    vpBanner.currentItem = 0
                }
                mainHandler.postDelayed(this, 3000)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgNavDark)
        }

        viewModel.getBanners().observe(this, observerBanner)
        viewModel.getSpotlight().observe(this, observerSpotlight)

        btnCart.setOnClickListener {
            startActivity<CartActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        tvCartCounter.text = CartHelper.getCartCounter().toString()
    }
}