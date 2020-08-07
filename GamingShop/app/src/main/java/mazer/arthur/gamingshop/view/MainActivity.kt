package mazer.arthur.gamingshop.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.data.ApiHelper
import mazer.arthur.gamingshop.data.IntentConstants
import mazer.arthur.gamingshop.data.RetrofitHelper
import mazer.arthur.gamingshop.data.ViewModelFactory
import mazer.arthur.gamingshop.models.Banner
import mazer.arthur.gamingshop.models.GameDetails
import mazer.arthur.gamingshop.models.Status
import mazer.arthur.gamingshop.view.adapter.BannerAdapter
import mazer.arthur.gamingshop.view.adapter.SpotlightAdapter
import mazer.arthur.gamingshop.view.listeners.BannerClickListener
import mazer.arthur.gamingshop.view.listeners.SpotlightClicked

class MainActivity : AppCompatActivity(), BannerClickListener, SpotlightClicked {

    private lateinit var viewModel: MainViewModel
    private var bannerAdapter = BannerAdapter(this)
    private var spotlightAdapter = SpotlightAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupView()
        registerObservers()

    }

    private fun setupView() {
        setupBannersRecyclerView()
        setupSpotlightRecyclerView()
    }

    private fun setupBannersRecyclerView() {
        rvBannerGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvBannerGames.itemAnimator = DefaultItemAnimator()
        rvBannerGames.adapter = bannerAdapter
    }

    private fun setupSpotlightRecyclerView(){
        rvGameCatalog.layoutManager = LinearLayoutManager(this)
        rvGameCatalog.itemAnimator = DefaultItemAnimator()
        rvGameCatalog.adapter = spotlightAdapter
    }

    private fun registerObservers() {
        viewModel.getBanners().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        rvBannerGames.visibility = View.VISIBLE
                        response.data.let { bannerList ->
                            bannerAdapter.bannerList = bannerList ?: return@Observer
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }

            }
        })
        viewModel.getSpotlight().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        rvGameCatalog.visibility = View.VISIBLE
                        response.data.let { spotlightList ->
                            val aaaa = spotlightList
                            spotlightAdapter.gameDetailsList = spotlightList ?: return@Observer
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }

            }
        })
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitHelper.api))
        ).get(MainViewModel::class.java)
    }

    override fun onBannerClicked(banner: Banner) {

    }

    override fun onSpotlightClicked(gameDetails: GameDetails) {
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra(IntentConstants.EXTRA_ID_GAME, gameDetails.id)
        startActivity(intent)
    }
}