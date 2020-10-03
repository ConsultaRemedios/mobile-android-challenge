
package br.com.challenge.consultaremedios

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import br.com.challenge.consultaremedios.adapter.BannerAdapter
import br.com.challenge.consultaremedios.adapter.GamesAdapter
import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.MobileTestService
import br.com.challenge.consultaremedios.model.Banner
import br.com.challenge.consultaremedios.model.Game
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.Normalizer
import java.util.*

const val EXTRA_GAME_ID = "br.com.challenge.consultaremedios.GAME_ID"
const val REQUEST_CODE_SPEECH_INPUT = 1001

class MainActivity : AppCompatActivity(), GamesAdapter.OnGameTapListener {
    private var mGames: List<Game>? = null
    private val mApi = MobileTestService.buildService(Endpoints::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val carousel: ImageCarousel = findViewById(R.id.banner)
//        carousel.onItemClickListener = object: OnItemClickListener {
//            override fun onClick(position: Int, carouselItem: CarouselItem) {
//                openURL(carouselItem.caption)
//            }
//
//            override fun onLongClick(position: Int, dataObject: CarouselItem) { }
//        }

        val getBannersCall = mApi.getBanners()
        getBannersCall.enqueue(object : Callback<List<Banner>> {
            override fun onResponse(call: Call<List<Banner>>, response: Response<List<Banner>>) {
                if (response.isSuccessful) {
//                    val list = mutableListOf<CarouselItem>()
                    val banners = response.body().orEmpty()
                    val banner = findViewById<ViewPager>(R.id.banner)
                    val adapter = BannerAdapter(this@MainActivity, banners.orEmpty())
                    banner.adapter = adapter
                    banner.setPadding(130, 0, 130, 0)
//                    banners?.forEach { list.add(CarouselItem(imageUrl = it.image, it.url)) }
//                    carousel.addData(list)
                }
            }

            override fun onFailure(call: Call<List<Banner>>, t: Throwable) {
//                Snackbar.make(carousel, "Não foi possíverl bla bla bla", Snackbar.LENGTH_LONG)
            }
        })

        // load list GridView
        loadGames()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val searchText = result?.joinToString { it }
                    searchGame(searchText.orEmpty())
                }
            }
        }
    }

    override fun onGameTap(position: Int) {
        val game = mGames?.get(position)
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra(EXTRA_GAME_ID, game?.id)
        }
        startActivity(intent)
    }

    fun openURL(url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun loadGames() {
        mApi.getSpotlight().enqueue(object: Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    val recyclerView: RecyclerView = findViewById(R.id.games_view)
                    recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)

                    mGames = response.body()
                    val adapter = GamesAdapter(this@MainActivity, mGames.orEmpty(), this@MainActivity)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, getString(R.string.warn_games_not_loaded), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.warn_games_not_loaded), Toast.LENGTH_LONG).show()
            }
        })


    }

    fun speak(view: View) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.label_speech_search_hint))
        }

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (err: Exception) {
            Toast.makeText(this, "${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun searchGame(searchText: String) {
        val query = searchText
            .toLowerCase(Locale.getDefault())
            .unaccent()

        mApi.searchGames(query).enqueue(object: Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.warn_games_not_loaded), Toast.LENGTH_LONG).show()
            }
        })
    }

    fun CharSequence.unaccent(): String {
        val re = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return re.replace(temp, "")
    }
}