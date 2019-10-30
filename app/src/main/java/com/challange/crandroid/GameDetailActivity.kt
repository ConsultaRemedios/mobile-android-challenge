package com.challange.crandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.challange.crandroid.adapter.GameDetailImageSlider
import com.challange.crandroid.api.GameCheckoutServiceInitializer
import com.challange.crandroid.data.response.GameDetails
import com.opensooq.pluto.listeners.OnItemClickListener
import com.skyhope.showmoretextview.ShowMoreTextView
import io.github.bffcorreia.fole.Fole
import kotlinx.android.synthetic.main.activity_game_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        loadGame(intent.getIntExtra("gameId", 0))

//        text_view_show_more.setShowingLine(7)
//        text_view_show_more.addShowMoreText("Ler Mais")
//        text_view_show_more.addShowLessText("Ler Menos")
    }

    private fun loadGame(gameId: Int) {
        GameCheckoutServiceInitializer().gameCheckoutService().getGameDetails(gameId)
            .enqueue(object: Callback<GameDetails> {
                override fun onResponse(call: Call<GameDetails>, response: Response<GameDetails>) {
                    val gameDetail = response.body()

                    val images = gameDetail?.images as MutableList<String>
                    val adapter = GameDetailImageSlider(images)
                    plutoGameDetails.create(adapter, lifecycle = lifecycle)
                    plutoGameDetails.setCustomIndicator(custom_indicator)


                    game_title.text = gameDetail.name

                    Fole.with(expandableTextView)
                        .text(gameDetail.description)
                        .maxLines(6)
                        .toggleView(toggle_view)

//                    expandable_text.text = gameDetail.description
                }

                override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                    TODO("Notify can't load games")
                }
            })
    }
}
