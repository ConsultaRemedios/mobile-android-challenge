package teste.exemplo.com.gamecommerce.View.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import teste.exemplo.com.gamecommerce.R
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import teste.exemplo.com.gamecommerce.Service.GameService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import teste.exemplo.com.gamecommerce.Presenter.Main.IMainActivityPresenter
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import teste.exemplo.com.gamecommerce.Presenter.Main.MainActivityPresenter
import teste.exemplo.com.gamecommerce.Util.ConnectivityUtil


class MainActivity: AppCompatActivity(), IMainActivityView {


    var service: GameService = GameService()
    private lateinit var adapter: GamesAdapter
    private lateinit var mainActivityPresenter: IMainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter = MainActivityPresenter(this)
        configureRecyclerView()
        setAdapter()
        checkConnectivity()
        getData()
//        val imageList = ArrayList<SlideModel>()
//        imageList.add(SlideModel(R.drawable.game_super_mario_odyssey))
//        imageList.add(SlideModel(R.drawable.game_super_mario_odyssey))
//        imageList.add(SlideModel(R.drawable.game_super_mario_odyssey))
//        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
//        imageSlider.setImageList(imageList)
    }

    override fun configureRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(
            this,2
        )
        recyclerView.layoutManager = layoutManager
    }

    override fun setAdapter() {
        adapter = GamesAdapter(this)
        recyclerView.adapter = adapter
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
                mainActivityPresenter.getGamesData(service)
            }
        }
        thread.start()
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
        setLoadingVisibility(View.GONE)
    }

}
