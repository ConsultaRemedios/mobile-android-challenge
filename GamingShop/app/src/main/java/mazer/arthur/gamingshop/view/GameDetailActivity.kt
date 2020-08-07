package mazer.arthur.gamingshop.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.data.ApiHelper
import mazer.arthur.gamingshop.data.RetrofitHelper
import mazer.arthur.gamingshop.data.ViewModelFactory
import mazer.arthur.gamingshop.models.GameDetails
import mazer.arthur.gamingshop.models.Status

class GameDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: GameDetailViewModel
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        setupViewModel()
        registerObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitHelper.api))
        ).get(GameDetailViewModel::class.java)
        viewModel.extras = intent.extras
    }


    private fun registerObservers() {
        viewModel.getGameDetail().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        response.data.let { gameDetail ->
                            setGameDetail(gameDetail ?: return@Observer)
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
    }


    private fun setGameDetail(gameDetails: GameDetails){
        Picasso.get().load(gameDetails.image).into(ivGamePoster)
        tvGameTitle?.text = gameDetails.title
        tvRatingNumber?.text = gameDetails.rating.toString()
        ratingBar.rating = gameDetails.stars.toFloat()
        tvReviewNumber.text = getString(R.string.reviews_label, gameDetails.reviews.toString())
        tvOriginalPrice.text = gameDetails.price.toString()
        tvDiscountedPrice.text = gameDetails.discount.toString()
        tvGameDescription.text = gameDetails.description
    }
}