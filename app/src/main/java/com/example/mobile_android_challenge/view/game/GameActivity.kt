package com.example.mobile_android_challenge.view.game


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mobile_android_challenge.R
import com.example.mobile_android_challenge.model.GameItem
import com.example.mobile_android_challenge.util.isVisible
import com.example.mobile_android_challenge.util.loadImage
import com.example.mobile_android_challenge.view.cart.CartActivity
import com.example.mobile_android_challenge.view_model.GameViewModel
import com.example.mobile_android_challenge.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.custom_toobar.*
import kotlinx.android.synthetic.main.custom_toobar.view.*
import kotlinx.android.synthetic.main.game_card_bottom.view.*
import kotlinx.android.synthetic.main.item_game_adapter.view.*
import javax.inject.Inject

class GameActivity : AppCompatActivity() {

    @Inject
    lateinit var gameVMFactory: ViewModelFactory<GameViewModel>

    private val gameViewModel by lazy {
        ViewModelProviders.of(this, gameVMFactory)[GameViewModel::class.java]
    }

    private val gameObserver = Observer<GameItem>(::onMovieFetched)
    private val gameObserverCart = Observer<Int>(::onChangeCartSize)
    private val gameObserverCartLimit = Observer<String>(::showMaxItem)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        game_view_loading.isVisible = true
        val data: Bundle? = intent.extras
        var gameId = data?.getLong("game_id")

        gameViewModel.data.observe(this, gameObserver)
        gameViewModel.sizeCart.observe(this, gameObserverCart)
        gameViewModel.msgLimitItem?.observe(this, gameObserverCartLimit)
        if (gameId != null) {
            gameViewModel.fetchGameItem(gameId.toLong())
        }
        gameViewModel.loadCountCart(this.baseContext, false)

        img_cart.setOnClickListener {
            val intent = Intent(this.baseContext, CartActivity::class.java)
            this.baseContext.startActivity(intent)
        }
    }

    private fun onMovieFetched(gameItem: GameItem?) {
        if(gameItem != null){
            main_toolbar.tv_title_game.text = gameItem.platform.toUpperCase()

            game_bottom_view.tv_game_price.text = getString(R.string.item_price,
                gameItem.price.toString())
            img_detail_cover.loadImage(gameItem.image)
            tv_detail_game_title.text = gameItem.name
            tv_detail_game_description.text = gameItem.description

            tv_detail_read_more.setOnClickListener {
                tv_detail_game_description.maxLines = 30
                tv_detail_read_more.visibility = View.GONE
            }

            game_bottom_view.btn_game_add_to_cart.setOnClickListener {
                gameViewModel.addItemCart(this.baseContext, gameItem)
            }

            tv_detail_read_more.visibility = View.VISIBLE
            game_bottom_view.tv_game_price.visibility = View.VISIBLE
            game_bottom_view.tv_game_freight_price.visibility = View.VISIBLE
            game_bottom_view.tv_game_freight_price.visibility = View.VISIBLE
        }
        game_view_loading.isVisible = false
    }

    private fun onChangeCartSize(cartSixe: Int?) {
        main_toolbar.tv_cart_count.isVisible = cartSixe!! > 0
        main_toolbar.tv_cart_count.text = cartSixe.toString()
    }

    private fun showMaxItem(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
