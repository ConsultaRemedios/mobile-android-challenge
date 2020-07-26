package com.games.ecommerce.main.ui.activity.gamedetails

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.Game
import com.games.ecommerce.main.ui.activity.gamelist.ListActivity
import com.games.ecommerce.utils.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_gamedetail.*
import javax.inject.Inject

class GameDetailActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var game: Game
    private val viewModel: GameDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GameDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamedetail)
        setValues()
        viewModel.checkGameStatus(game)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        observe(viewModel.isOnCart) {
            changeCartStatus(it)
        }
    }

    private fun changeCartStatus(isOnCart: Boolean) {
        if (isOnCart) {
            addtocart_btn.setBackgroundResource(R.drawable.circle_red_shape)
            return
        }
        addtocart_btn.setBackgroundResource(R.drawable.circle_white_shape)
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkGameStatus(game)
    }

    private fun setupListeners() {
        back_button_detail.setOnClickListener {
            super.onBackPressed()
        }
        addtocart_btn.setOnClickListener {
            viewModel.addOrRemoveOfCart(game)
        }
    }

    private fun setValues() {
        game = intent.getSerializableExtra("game") as Game
        image_detail.loadImg(game.image)
        title_detail.text = game.title
        rate_detail.text = game.rating.toString()
        reviews_detail.text = game.reviews.toString() + " reviews"
        oldprice_detail.text = ("de " + game.price.asCurrency()).asStrokeText()
        price_detail.text = (game.price - game.discount).asCurrency()
        description_detail.text = game.description
        ratingbar_detail.rating = game.rating.toFloat()
    }

    override fun onBackPressed() {
        val intent = Intent(this, ListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}