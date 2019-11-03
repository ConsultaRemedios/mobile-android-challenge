package teste.exemplo.com.gamecommerce.View.Game

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import teste.exemplo.com.gamecommerce.Presenter.Game.GamePresenter
import teste.exemplo.com.gamecommerce.Presenter.Game.IGamePresenter
import teste.exemplo.com.gamecommerce.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_game.*
import android.content.Intent
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.loadingImageView
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Util.ConnectivityUtil
import teste.exemplo.com.gamecommerce.Util.MoneyUtil
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class GameFragment(contentLayoutId: Int) : Fragment(contentLayoutId), IGameFragmentView {

    lateinit var gamePresenter: IGamePresenter

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamePresenter = GamePresenter(this)

        showLoading()
        checkConnectivity()
        getData()
    }

    override fun showLoading() {
        Glide.with(activity!!)
            .load(R.drawable.loading)
            .placeholder(R.drawable.loading)
            .into(loadingImageView)
        setLoadingVisibility(View.VISIBLE)
    }

    override fun setLoadingVisibility(visibility: Int) {
        loadingImageView.visibility = visibility
    }

    override fun checkConnectivity() {
        if (!ConnectivityUtil.isNetworkConnected(activity!!.applicationContext))
            Snackbar.make(
                scrollView2,
                getString(R.string.not_connected),
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.connect) {
                    startActivityForResult(
                        Intent(
                            android.provider.Settings.ACTION_SETTINGS
                        ), 0
                    )
                }
                .setActionTextColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorBlue)).show()
    }

    override fun showTryAgainSnackbar() {
        Snackbar.make(
            recyclerView,
            getString(R.string.failed_load_data),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.try_again)) { getData() }
            .setActionTextColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorBlue))
            .show()
    }

    override fun getData() {
        val thread = object : Thread() {
            override fun run() {
                gamePresenter.getGameDataById()
            }
        }
        thread.start()
    }

    @SuppressLint("SetTextI18n")
    override fun updateGame(){
        val game = Cache.getGame()
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(game.image))
        image_slider.setImageList(imageList)
        setDescriptionVisibility(!game.description.equals(""))
        game_name.text = game.name
        price.text = formatMoney(game.price)
        delivery_tax.text = "R$ 10,00"
        setLoadingVisibility(View.GONE)
    }

    override fun setDescriptionVisibility(visible: Boolean){
        if(visible) {
            description.visibility = View.GONE
            read_more.visibility = View.GONE
        } else {
            description.visibility = View.VISIBLE
            read_more.visibility = View.VISIBLE
        }
    }

}