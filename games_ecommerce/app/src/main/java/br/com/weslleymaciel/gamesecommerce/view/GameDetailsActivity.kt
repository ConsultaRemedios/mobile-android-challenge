package br.com.weslleymaciel.gamesecommerce.view

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.common.utils.CartHelper
import br.com.weslleymaciel.gamesecommerce.common.utils.loadImage
import br.com.weslleymaciel.gamesecommerce.common.utils.numberToPrice
import br.com.weslleymaciel.gamesecommerce.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast
import java.lang.String

class GameDetailsActivity: AppCompatActivity() {

    private val viewModel = GamesViewModel()

    private val observerGame = Observer<Game?> {
        it?.let {
            loadGame(it)
            pbLoad.visibility = View.GONE
            ivGamePhoto.visibility = View.VISIBLE
            llTopLoad.visibility = View.GONE
            llTopContent.visibility = View.VISIBLE
            llDescriptionLoad.visibility = View.GONE
            tvDescription.visibility = View.VISIBLE
        }
    }

    private val id by lazy {
        intent?.extras?.getInt("ID")
    }

    private fun loadGame(game: Game){
        ivGamePhoto.loadImage(game.image!!, R.drawable.placeholder)
        tvTitle.text = game.title
        tvRating.text = game.rating.toString()
        tvReviews.text = String.format(resources.getString(R.string.reviews), game.reviews.toString())
        tvDescription.text = game.description

        if (game.discount.toFloat() > 0.0){
            tvPrice.visibility = View.VISIBLE
            tvPrice.text = String.format(resources.getString(R.string.discount), numberToPrice(game.price))
            tvDiscount.text = numberToPrice( game.price.toFloat() - game.discount.toFloat())
            tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            tvPrice.visibility = View.GONE
            tvDiscount.text = numberToPrice(game.price)
        }

        ivStar1.setColorFilter(ContextCompat.getColor(this,R.color.white))
        ivStar2.setColorFilter(ContextCompat.getColor(this,R.color.white))
        ivStar3.setColorFilter(ContextCompat.getColor(this,R.color.white))
        ivStar4.setColorFilter(ContextCompat.getColor(this,R.color.white))
        ivStar5.setColorFilter(ContextCompat.getColor(this,R.color.white))

        for(num in 1..game.rating!!.toInt()){
            when(num){
                1 -> {ivStar1.setColorFilter(ContextCompat.getColor(this,R.color.yellow))}
                2 -> {ivStar2.setColorFilter(ContextCompat.getColor(this,R.color.yellow))}
                3 -> {ivStar3.setColorFilter(ContextCompat.getColor(this,R.color.yellow))}
                4 -> {ivStar4.setColorFilter(ContextCompat.getColor(this,R.color.yellow))}
                5 -> {ivStar5.setColorFilter(ContextCompat.getColor(this,R.color.yellow))}
            }
        }

        if (CartHelper.isItemOnCart(game.id.toInt())){
            hideBtnAdd()
        }else{
            showBtnAdd()
        }

        btnCartAdd.setOnClickListener {
            CartHelper.addItemToCart(game)
            hideBtnAdd()
            toast(resources.getString(R.string.cart_item_added))
        }

        btnCartRemove.setOnClickListener {
            CartHelper.removeItemFromCart(game.id.toInt())
            showBtnAdd()
            toast(resources.getString(R.string.cart_item_removed))
        }
    }

    private fun showBtnAdd(){
        btnCartAdd.show()
        btnCartRemove.hide()
    }

    private fun hideBtnAdd(){
        btnCartAdd.hide()
        btnCartRemove.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        viewModel.getGameDetail(id!!).observe(this, observerGame)
    }
}