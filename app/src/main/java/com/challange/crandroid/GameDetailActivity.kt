package com.challange.crandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.challange.crandroid.adapter.GameDetailImageSlider
import com.challange.crandroid.api.GameCheckoutServiceInitializer
import com.challange.crandroid.data.CartItem
import com.challange.crandroid.data.response.Game
import com.challange.crandroid.data.response.GameDetails
import com.challange.crandroid.singleton.Cart
import com.challange.crandroid.utils.GenericUtils.Companion.brazilianNumberFormat
import kotlinx.android.synthetic.main.activity_game_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameDetailActivity : AppCompatActivity() {

    private lateinit var mGame: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mGame = intent.getSerializableExtra("game") as Game
        supportActionBar?.title = mGame.platform

        gameDescriptionToggleViewExpand.setOnClickListener(expandDescription)
        gameDescriptionToggleViewCollapse.setOnClickListener(collapseDescription)
        buttonAdicionarCarrinho.setOnClickListener(addToCart)

        // Perform collapse function to start activity with game description collapsed
        gameDescriptionToggleViewCollapse.performClick()

        loadGameDetails(mGame)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_game_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private val expandDescription = View.OnClickListener {
        gameDescriptionTextView.maxLines = Int.MAX_VALUE
        gameDescriptionToggleViewExpand.visibility = View.GONE
        gameDescriptionToggleViewCollapse.visibility = View.VISIBLE
    }

    private val collapseDescription = View.OnClickListener {
        gameDescriptionTextView.maxLines = 6
        gameDescriptionToggleViewExpand.visibility = View.VISIBLE
        gameDescriptionToggleViewCollapse.visibility = View.GONE
    }

    private val addToCart = View.OnClickListener {
        if (!Cart.itens.any { it.game == mGame })
            Cart.itens.add(CartItem(mGame, 1, 0.00))
        else
            Toast.makeText(this, "Jogo já adicionado ao carrinho", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
    }

    private fun updateViewWithGameDetails(gameDetails: GameDetails) {
        val images = gameDetails.images as MutableList<String>
        val adapter = GameDetailImageSlider(images)
        plutoGameDetails.create(adapter, lifecycle = lifecycle)
        plutoGameDetails.setCustomIndicator(plutoIndicator)

        gameTitle.text = gameDetails.name
        gameDescriptionTextView.text = gameDetails.description
        priceTag.text = brazilianNumberFormat().format(gameDetails.price)

        plutoGameDetails.visibility = View.VISIBLE
        plutoIndicator.visibility = View.VISIBLE
        separator.visibility = View.VISIBLE
        gameTitle.visibility = View.VISIBLE
        gameDescription.visibility = View.VISIBLE
        shadowTop.visibility = View.VISIBLE
        bottomMenu.visibility = View.VISIBLE
        progressIndicator.visibility = View.GONE
    }

    private fun loadGameDetails(game: Game) {
        GameCheckoutServiceInitializer().gameCheckoutService().getGameDetails(game.id)
            .enqueue(object : Callback<GameDetails> {
                override fun onResponse(call: Call<GameDetails>, response: Response<GameDetails>) {
                    if (response.isSuccessful) {
                        updateViewWithGameDetails(response.body()!!)
                    } else {
                        TODO("Notify can't load games")
                    }
                }

                override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                    TODO("Notify can't load games")
                }
            })
    }
}
