package com.games.ecommerce.main.ui.activity.gamedetails

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProviders
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.utils.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_gamedetail.*
import java.security.AccessController.getContext
import javax.inject.Inject

class GameDetailActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var gameRepositoryResponse: GameRepositoryResponse

    private val viewModel: GameDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GameDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamedetail)
        setValues()
        viewModel.checkGameStatus(gameRepositoryResponse)
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
            addtocart_btn.setImageResource(R.drawable.shopping_cart_remove)
            return
        }
        addtocart_btn.setBackgroundResource(R.drawable.circle_white_shape)
        addtocart_btn.setImageResource(R.drawable.shopping_cart)
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkGameStatus(gameRepositoryResponse)
    }

    private fun setupListeners() {
        back_button_detail.setOnClickListener {
            super.onBackPressed()
        }
        addtocart_btn.setOnClickListener {
            viewModel.addOrRemoveOfCart(gameRepositoryResponse)
        }
    }

    private fun setValues() {
        gameRepositoryResponse =
            intent.getSerializableExtra(SERIALIZABLE_EXTRA_GAMES) as GameRepositoryResponse
        image_detail.loadImg(gameRepositoryResponse.image)
        title_detail.text = gameRepositoryResponse.title
        rate_detail.text = gameRepositoryResponse.rating.toString()
        reviews_detail.text = String.format("%s reviews", gameRepositoryResponse.reviews.toString())
        oldprice_detail.text =
            String.format("de %s", gameRepositoryResponse.originalPrice.asCurrency()).asStrokeText()
        price_detail.text = (gameRepositoryResponse.price).asCurrency()
        description_detail.text = gameRepositoryResponse.description
        ratingbar_detail.rating = gameRepositoryResponse.rating.toFloat()
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        private const val SERIALIZABLE_EXTRA_GAMES = "game"
    }
}