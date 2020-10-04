package br.com.challenge.consultaremedios

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.MobileTestService
import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.db.viewmodel.CartViewModel
import br.com.challenge.consultaremedios.model.Game
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_game_details.*
import br.com.challenge.consultaremedios.utils.GenericUtils.Companion.brazilianNumberFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailsActivity : AppCompatActivity() {
    private val mApi = MobileTestService.buildService(Endpoints::class.java)
    private lateinit var cartViewModel: CartViewModel

    var mGame: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        showCustomUI()

        // Get the Intent that started this activity and extract the string
        val gameId = intent.getIntExtra(EXTRA_GAME_ID, 0)
        loadGameDetails(gameId)
    }

    private fun loadGameDetails(gameId: Int) {
        mApi.getGameDetails(gameId).enqueue(object : Callback<Game> {
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.isSuccessful) {
                    mGame = response.body()

                    findViewById<TextView>(R.id.title).apply {
                        text = mGame?.title
                    }
                    findViewById<TextView>(R.id.game_rating).apply {
                        text = mGame?.rating.toString()
                    }
                    findViewById<RatingBar>(R.id.game_rating_bar).apply {
                        rating = mGame?.stars?.toFloat() ?: 0f
                    }
                    findViewById<TextView>(R.id.game_reviews).apply {
                        text = String.format("%s reviews",mGame?.reviews)
                    }
                    findViewById<TextView>(R.id.game_price).apply {
                        text = String.format("de %s", brazilianNumberFormat().format(mGame?.price))
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    val price = mGame?.price ?: 0.0
                    val discount = mGame?.discount ?: 0.0
                    findViewById<TextView>(R.id.game_price_with_discount).apply {
                        text = brazilianNumberFormat().format(price.minus(discount))
                    }
                    findViewById<TextView>(R.id.game_description).apply {
                        text = mGame?.description
                    }

                    val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
                    Glide.with(this@GameDetailsActivity)
                        .load(mGame?.image)
                        .apply(requestOptions)
                        .into(findViewById(R.id.game_boxart))
                } else {
                    Toast.makeText(
                        this@GameDetailsActivity,
                        getString(R.string.warn_games_details_not_loaded),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(
                    this@GameDetailsActivity,
                    getString(R.string.warn_games_details_not_loaded),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun showCustomUI() {
//        val decorView: View = window.decorView
//        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun addGameToChart(view: View) {
        val item = CartItem(
            gameId = mGame?.id!!,
            title = mGame?.title!!,
            boxArtUrl = mGame?.image!!,
            quantity = 1,
            unitPrice = mGame?.price!!,
            unitPriceWithDiscount = (mGame?.price!! - mGame?.discount!!)
        )

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.insert(item)

        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
}