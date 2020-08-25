package com.games.ecommerce.main.ui.activity.gamelist

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.Banner
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.ui.activity.gamedetails.GameDetailActivity
import com.games.ecommerce.main.ui.activity.shoppingcart.ShoppingCartActivity
import com.games.ecommerce.main.ui.fragment.searchgame.SearchGameFragment
import com.games.ecommerce.utils.ViewModelFactory
import com.games.ecommerce.utils.observe
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GameListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GameListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        searchTextGame.setText("")
        viewModel.fetchData()
    }

    private fun setupListeners() {

        searchTextGame.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.checkFragmentVisibility(s.toString().length)
                if (s.toString().equals("")) return
                viewModel.searchGame(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }
        })

        floatButton.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun switchFragment(visible: Boolean) {
        val fragment = SearchGameFragment()
        val fragmentManager = supportFragmentManager
        if (visible) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.listActivity, fragment)
            fragmentTransaction.commit()
            return
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.listActivity, Fragment())
        fragmentTransaction.commit()
    }

    private fun setupObservers() {
        observe(viewModel.games) {
            addGames(it)
        }
        observe(viewModel.banners) {
            addBanners(it)
        }
        observe(viewModel.isSearchTextVisible) {
            switchFragment(it)
        }
        observe(viewModel.cartAmount) {
            floatbutton_text.text = it.toString()
        }
        observe(viewModel.error) {
            showInfoDialog(it)
        }
    }

    private fun showInfoDialog(error: String) {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            setMessage(error)
            setTitle(getString(R.string.warning_title))
            setPositiveButton(
                getString(R.string.warning_reload_button)
            ) { _, _ ->
                viewModel.fetchData()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun addGames(gameRepositoryResponse: List<GameRepositoryResponse>) {
        games_recyclerView.apply {
            layoutManager = GridLayoutManager(this@ListActivity, 2)
            adapter =
                GameAdapter(gameRepositoryResponse) {
                    startDetail(it)
                }
        }
    }

    private fun addBanners(banners: List<Banner>) {
        banners_recyclerview.apply {
            layoutManager =
                LinearLayoutManager(this@ListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                BannerAdapter(
                    banners
                ) {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(this@ListActivity, Uri.parse(it.url))
                }
        }
    }

    private fun startDetail(gameRepositoryResponse: GameRepositoryResponse) {
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra("game", gameRepositoryResponse)
        startActivity(intent)
    }

}
