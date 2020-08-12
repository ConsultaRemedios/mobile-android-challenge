package br.com.weslleymaciel.gamesecommerce.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
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
import br.com.weslleymaciel.gamesecommerce.view.adapters.SearchAdapter
import br.com.weslleymaciel.gamesecommerce.view.adapters.SpotlightAdapter
import br.com.weslleymaciel.gamesecommerce.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class HomeActivity : AppCompatActivity() {
    private val viewModel = GamesViewModel()
    private val SPEECH_INPUT = 123
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

    private fun configureSearch(){
        var searchAdapter = SearchAdapter(this)
        acSearch.setAdapter(searchAdapter)
        acSearch.threshold = 1
        acSearch.validator = searchAdapter
        acSearch.setOnItemClickListener { _, _, i, _ ->
            run {
                acSearch.setText("")
                startActivity<GameDetailsActivity>("ID" to searchAdapter.getSelected(i)!!.id.toInt())
            }
        }
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

        iconMic.setOnClickListener {
            promptSpeechInput()
        }

        configureSearch()
    }

    override fun onResume() {
        super.onResume()
        tvCartCounter.text = CartHelper.getCartCounter().toString()
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech)
        )
        try {
            startActivityForResult(intent, SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            toast(resources.getString(R.string.speech_not_supported))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SPEECH_INPUT && resultCode == Activity.RESULT_OK && data != null){
            acSearch.setText(data!!.extras!!.getStringArrayList(RecognizerIntent.EXTRA_RESULTS)!![0])
        }
    }
}