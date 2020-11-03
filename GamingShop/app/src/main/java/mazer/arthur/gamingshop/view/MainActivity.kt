package mazer.arthur.gamingshop.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.data.remote.ApiHelper
import mazer.arthur.gamingshop.utils.IntentConstants
import mazer.arthur.gamingshop.data.remote.RetrofitHelper
import mazer.arthur.gamingshop.utils.ViewModelFactory
import mazer.arthur.gamingshop.domain.models.Banner
import mazer.arthur.gamingshop.domain.models.GameDetails
import mazer.arthur.gamingshop.domain.models.Status
import mazer.arthur.gamingshop.view.adapter.BannerAdapter
import mazer.arthur.gamingshop.view.adapter.SpotlightAdapter
import mazer.arthur.gamingshop.utils.listeners.BannerClickListener
import mazer.arthur.gamingshop.utils.listeners.SpotlightClicked
import mazer.arthur.gamingshop.view.adapter.SearchListAdapter

class MainActivity : AppCompatActivity(), BannerClickListener, SpotlightClicked {

    private lateinit var viewModel: MainViewModel
    private var bannerAdapter = BannerAdapter()
    private var spotlightAdapter = SpotlightAdapter(this)
    private var searchAdapter = SearchListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupView()
        registerObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTotalCartItems()
    }

    private fun setupView() {
        setupBannersRecyclerView()
        setupSpotlightRecyclerView()
        setupSearchRecyclerView()
        setupCartButton()
        setupSearchView()
    }

    private fun setupSearchRecyclerView() {
        rvGamesSearched.layoutManager = LinearLayoutManager(this)
        rvGamesSearched.itemAnimator = DefaultItemAnimator()
        rvGamesSearched.adapter = searchAdapter
    }

    private fun setupSearchView() {
        searchBarGames?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    layoutPanelSearch.visibility = View.GONE
                }else {
                    layoutPanelSearch.visibility = View.VISIBLE
                    viewModel.searchGame(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    layoutPanelSearch.visibility = View.GONE
                }else {
                    layoutPanelSearch.visibility = View.VISIBLE
                    viewModel.searchGame(newText)
                }
                return true
            }

        })
    }

    private fun setupCartButton() {
        fbAddToCart?.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBannersRecyclerView() {
        rvBannerGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvBannerGames.itemAnimator = DefaultItemAnimator()
        rvBannerGames.adapter = bannerAdapter
    }

    private fun setupSpotlightRecyclerView(){
        rvGameCatalog.layoutManager = GridLayoutManager(this,2)
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
        viewModel.eventLiveData.observe(this, Observer {
            when(it){
                is MainViewModel.ViewEvent.NumItemCartChanged -> {
                        tvQuantItemsCart?.visibility = View.VISIBLE
                        tvQuantItemsCart?.text = it.quant.toString()
                }
                is MainViewModel.ViewEvent.EmptyCart -> {
                    tvQuantItemsCart?.visibility = View.GONE
                }
                is MainViewModel.ViewEvent.OnGamesSearched -> {
                    showListGameSearchResult(it.gameList)
                }
            }
        })
    }

    private fun showListGameSearchResult(listGames: List<GameDetails>){
        searchAdapter.addSearchedGames(listGames)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this,
            ViewModelFactory(
                ApiHelper(
                    RetrofitHelper.api
                ), applicationContext
            )
        ).get(MainViewModel::class.java)
    }

    override fun onBannerClicked(banner: Banner) {
        Log.d("banner_url", banner.url)
    }

    override fun onSpotlightClicked(gameDetails: GameDetails) {
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra(IntentConstants.EXTRA_GAME_DETAILS, gameDetails)
        startActivity(intent)
    }
}