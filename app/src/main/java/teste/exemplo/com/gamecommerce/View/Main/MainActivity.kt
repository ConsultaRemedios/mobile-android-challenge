package teste.exemplo.com.gamecommerce.View.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import teste.exemplo.com.gamecommerce.R
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import teste.exemplo.com.gamecommerce.Presenter.Main.IMainActivityPresenter
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Presenter.Main.MainActivityPresenter
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Util.ConnectivityUtil
import teste.exemplo.com.gamecommerce.View.Cart.CartFragment
import teste.exemplo.com.gamecommerce.View.Game.GameFragment




class MainActivity: AppCompatActivity(), IMainActivityView {


    private lateinit var adapter: GamesAdapter
    private lateinit var mainActivityPresenter: IMainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = MainActivityPresenter(this)
        configureToolbar(getString(R.string.app_name), false)
        configureRecyclerView()
        configureAdapter()
        checkConnectivity()
        getData()
    }

    override fun configureToolbar(title: String, hasBackArrow: Boolean){
        if(hasBackArrow){
            backArrow.visibility = View.VISIBLE
        } else {
            backArrow.visibility = View.GONE
        }
        toolbar_title.text = title
        cart_items.text = Cart.totalItems.toString()
        backArrow.setOnClickListener { onBackPressed() }

    }

    override fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(
            this,2
        )
        recyclerView.layoutManager = layoutManager
    }

    override fun configureAdapter() {
        adapter = GamesAdapter(this)
        recyclerView.adapter = adapter

        adapter.onItemClick = { game ->
            configureToolbar(game.platform, true)
            Cache.setSelectedGameId(game.id)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_container, GameFragment(), "GameFragment")
                .addToBackStack("GameFragment")
                .commit()
        }
    }


    override fun checkConnectivity() {
        if (!ConnectivityUtil.isNetworkConnected(this))
            Snackbar.make(
                recyclerView,
                getString(R.string.not_connected),
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(getString(R.string.connect)) {
                    startActivityForResult(
                        Intent(android.provider.Settings.ACTION_SETTINGS), 0
                    )
                }
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorBlue))
                .show()
    }



    override fun showLoading() {
        Glide.with(this)
            .load(R.drawable.loading)
            .placeholder(R.drawable.loading)
            .into(loadingImageView)
        setLoadingVisibility(View.VISIBLE)
    }

    override fun setLoadingVisibility(visibility: Int) {
        loadingImageView.visibility = visibility
    }

    override fun showTryAgainSnackbar() {
        Snackbar.make(
            recyclerView,
            getString(R.string.failed_load_data),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.try_again)) { getData() }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorBlue))
            .show()
    }

    private fun getData() {
        val thread = object : Thread() {
            override fun run() {
                mainActivityPresenter.getGamesData()
            }
        }
        thread.start()
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
        setLoadingVisibility(View.GONE)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0){
            configureToolbar(getString(R.string.app_name), false)
        }
    }

    override fun getToken(): String {
        return getString(R.string.token)
    }

    override fun search(view: View){
        //TODO: Implement search method
    }

    override fun goToCart(view: View){
        if(Cart.items.size > 0) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_container, CartFragment(), "CartFragment")
                .addToBackStack("CartFragment")
                .commit()
        } else {
            Toast.makeText(this,getString(R.string.empty_cart), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragments = supportFragmentManager.fragments
        supportFragmentManager.putFragment(
            outState,
            "FRAGMENT",
            fragments[fragments.size - 1]!!
        )
    }
}
