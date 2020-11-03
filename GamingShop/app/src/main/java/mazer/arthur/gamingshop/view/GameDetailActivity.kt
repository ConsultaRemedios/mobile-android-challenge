package mazer.arthur.gamingshop.view

import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.data.remote.ApiHelper
import mazer.arthur.gamingshop.data.remote.RetrofitHelper
import mazer.arthur.gamingshop.utils.ViewModelFactory
import mazer.arthur.gamingshop.domain.models.GameDetails
import mazer.arthur.gamingshop.domain.models.Status

class GameDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: GameDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        setupViewModel()
        setupView()
        registerObservers()

    }

    private fun setupView(){
        fbAddToCart?.setOnClickListener {
            viewModel.onCartButtonClicked()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            ViewModelFactory(
                ApiHelper(
                    RetrofitHelper.api
                ), applicationContext
            )
        ).get(GameDetailViewModel::class.java)
        viewModel.extras = intent.extras
    }

    private fun registerObservers() {
        viewModel.getGameDetail().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        setGameDetail(response.data ?: return@Observer)
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
        viewModel.getItemCart().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        response.data.let { countItens ->
                            if (countItens != null) {
                                setToggleCartButton(countItens > 0)
                            }
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
        viewModel.eventLiveData.observe(this, Observer {
            when (it){
                GameDetailViewModel.ViewEvent.ItemAdded -> {
                    setToggleCartButton(true)

                }
                GameDetailViewModel.ViewEvent.ItemRemoved -> {
                    setToggleCartButton(false)
                }
                GameDetailViewModel.ViewEvent.Error -> {

                }
            }
        })
    }

    //Pinta o floating button do carrinho de vermelho e troca o ícone sempre que o jogo já tenha sido adicionado
    //o botão volta a cor cinza e com ícone de adicionar ao carrinho se o item ainda não tiver sido adicionado.
    private fun setToggleCartButton(isGameAdded: Boolean){
        if (isGameAdded){
            fbAddToCart?.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24)
            fbAddToCart?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.colorAccent))
        }else{
            fbAddToCart?.setImageResource(R.drawable.ic_baseline_shopping_cart_24)
            fbAddToCart?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, android.R.color.white))
        }
    }

    private fun setGameDetail(gameDetails: GameDetails){
        Picasso.get().load(gameDetails.image).into(ivGamePoster)
        tvGameTitle?.text = gameDetails.title
        tvRatingNumber?.text = gameDetails.rating.toString()
        ratingBar.rating = gameDetails.stars.toFloat()
        tvReviewNumber.text = getString(R.string.reviews_label, gameDetails.reviews.toString())
        tvGameDescription.text = gameDetails.description

        tvOriginalPrice.text = getString(R.string.original_price_placeholder,  gameDetails.price.toString())
        tvDiscountedPrice.text =  getString(R.string.price_placeholder, gameDetails.discount.toString())
        tvOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }
}