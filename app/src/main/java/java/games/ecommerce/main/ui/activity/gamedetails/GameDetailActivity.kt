package java.games.ecommerce.main.ui.activity.gamedetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gamedetail.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.utils.asCurrency
import java.games.ecommerce.utils.asStrokeText
import java.games.ecommerce.utils.loadImg

class GameDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamedetail)
        setValues()
        setupListeners()
    }

    private fun setupListeners() {
        back_button_detail.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setValues() {
        val game = intent.getSerializableExtra("game") as Game
        image_detail.loadImg(game.image)
        title_detail.text = game.title
        rate_detail.text = game.rating.toString()
        reviews_detail.text = game.reviews.toString() + " reviews"
        oldprice_detail.text = ("de "+ game.price.asCurrency()).asStrokeText()
        price_detail.text = game.price.subtract(game.discount).asCurrency()
        description_detail.text = game.description
        ratingbar_detail.rating = game.rating.toFloat()
    }
}