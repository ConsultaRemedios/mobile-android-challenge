package br.com.challenge.consultaremedios.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import br.com.challenge.consultaremedios.R
import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.ApiService
import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.db.viewmodel.CartViewModel
import br.com.challenge.consultaremedios.model.Game
import br.com.challenge.consultaremedios.utils.GenericUtils.Companion.brazilianNumberFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_game_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailsActivity : AppCompatActivity() {
    private val api = ApiService.buildService(Endpoints::class.java)
    private lateinit var cartViewModel: CartViewModel

    var game: Game? = null
    var cartItem: CartItem? = null
    var gameId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        gameId = intent.getIntExtra(EXTRA_GAME_ID, 0)

        initView()
        initData(gameId!!)
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initData(gameId: Int) {
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        api.getGameDetails(gameId).enqueue(object : Callback<Game> {
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.isSuccessful) {
                    game = response.body()
                    cartViewModel.cartItems.observe(this@GameDetailsActivity, { items ->
                        val fabCartAction = findViewById<FloatingActionButton>(R.id.fab_cart_action)

                        cartItem = items.find { it.gameId == gameId }
                        if(cartItem == null) {
                            fabCartAction.apply {
                                backgroundTintList = AppCompatResources.getColorStateList(this@GameDetailsActivity,
                                    R.color.colorButtonSubmit
                                )
                                setImageDrawable(ContextCompat.getDrawable(this@GameDetailsActivity,
                                    R.drawable.ic_add_shopping_cart
                                ))
                                setOnClickListener(addGameToCartListener)
                            }
                        } else {
                            fabCartAction.apply {
                                backgroundTintList = AppCompatResources.getColorStateList(this@GameDetailsActivity,
                                    R.color.colorRed
                                )
                                setImageDrawable(ContextCompat.getDrawable(this@GameDetailsActivity,
                                    R.drawable.ic_remove_shopping_cart
                                ))
                                setOnClickListener(removeGameFromCartListener)
                            }
                        }
                        fabCartAction.visibility = View.VISIBLE
                        cartViewModel.cartItems.removeObservers(this@GameDetailsActivity)
                    })

                    val price = game?.price ?: 0.0
                    val discount = game?.discount ?: 0.0

                    val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
                    Glide.with(this@GameDetailsActivity)
                        .load(game?.image)
                        .apply(requestOptions)
                        .into(findViewById(R.id.game_boxart))

                    findViewById<TextView>(R.id.title).apply {
                        text = game?.title
                    }
                    findViewById<TextView>(R.id.game_rating).apply {
                        text = game?.rating.toString()
                    }
                    findViewById<RatingBar>(R.id.game_rating_bar).apply {
                        rating = game?.stars?.toFloat() ?: 0f
                    }
                    findViewById<TextView>(R.id.game_reviews).apply {
                        text = String.format("%s reviews",game?.reviews)
                    }
                    findViewById<TextView>(R.id.game_price).apply {
                        text = String.format("de %s", brazilianNumberFormat().format(game?.price))
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    findViewById<TextView>(R.id.game_price_with_discount).apply {
                        text = brazilianNumberFormat().format(price.minus(discount))
                    }
                    findViewById<TextView>(R.id.game_description).apply {
                        text = game?.description
                    }
                } else {
                    Toast.makeText(
                        this@GameDetailsActivity,
                        getString(R.string.message_error_api_request),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(
                    this@GameDetailsActivity,
                    getString(R.string.message_error_api_request),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    val addGameToCartListener = View.OnClickListener {
        val item = CartItem(
            gameId = game?.id!!,
            title = game?.title!!,
            boxArtUrl = game?.image!!,
            quantity = 1,
            unitPrice = game?.price!!,
            unitPriceWithDiscount = (game?.price!! - game?.discount!!)
        )
        cartViewModel.insert(item)

        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    val removeGameFromCartListener = View.OnClickListener {
        cartViewModel.delete(cartItem!!)
        Toast.makeText(
            this,
            getString(R.string.message_success_product_removed),
            Toast.LENGTH_LONG
        ).show()

        Intent(this, MainActivity::class.java).apply {
            navigateUpTo(this)
        }
    }
}