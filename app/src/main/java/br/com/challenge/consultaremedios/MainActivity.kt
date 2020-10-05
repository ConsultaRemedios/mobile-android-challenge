
package br.com.challenge.consultaremedios

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import br.com.challenge.consultaremedios.adapter.BannerAdapter
import br.com.challenge.consultaremedios.adapter.GamesAdapter
import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.MobileTestService
import br.com.challenge.consultaremedios.db.viewmodel.CartViewModel
import br.com.challenge.consultaremedios.model.Banner
import br.com.challenge.consultaremedios.model.Game
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.Normalizer
import java.util.*

const val EXTRA_GAME_ID = "br.com.challenge.consultaremedios.GAME_ID"
const val REQUEST_CODE_SPEECH_INPUT = 1001

class MainActivity : AppCompatActivity(), GamesAdapter.OnGameTapListener, BannerAdapter.BannerTapListener {

    private val api = MobileTestService.buildService(Endpoints::class.java)
    private var games: List<Game>? = null
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initView() {

        findViewById<EditText>(R.id.edit_search).apply {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                    searchGame(this.text.toString())

                // hide virtual keyboard
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.windowToken, 0)
                this.clearFocus()
                true
            }
        }
    }

    private fun initData() {
        // quantity badge
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        findViewById<TextView>(R.id.cart_count).apply {
            cartViewModel.cartItems.observe(this@MainActivity, { item ->
                text = item.map { it.quantity }.sum().toString()
            })
        }

        // banners
        val getBannersCall = api.getBanners()
        getBannersCall.enqueue(object : Callback<List<Banner>> {
            override fun onResponse(call: Call<List<Banner>>, response: Response<List<Banner>>) {
                if (response.isSuccessful) {
                    val banners = response.body().orEmpty()
                    val banner = findViewById<ViewPager>(R.id.banner)
                    val adapter = BannerAdapter(
                        this@MainActivity,
                        banners,
                        this@MainActivity
                    )
                    banner.adapter = adapter
                    banner.setPadding(60, 0, 60, 0)
                }
            }

            override fun onFailure(call: Call<List<Banner>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.message_error_api_request),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        // games
        api.getSpotlight().enqueue(object : Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    val recyclerView: RecyclerView = findViewById(R.id.games_view)
                    recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)

                    games = response.body()
                    val adapter = GamesAdapter(
                        this@MainActivity,
                        games.orEmpty(),
                        this@MainActivity
                    )
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.message_error_api_request),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.message_error_api_request),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val resultText = result?.joinToString { it }

                    findViewById<EditText>(R.id.edit_search).apply {
                        setText(resultText)
                        searchGame(this.text.toString())
                    }
                }
            }
        }
    }

    override fun onBannerTap(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            startActivity(this)
        }
    }

    override fun onGameTap(position: Int) {
        val game = games?.get(position)
        Intent(this, GameDetailsActivity::class.java).apply {
            putExtra(EXTRA_GAME_ID, game?.id)
            startActivity(this)
        }
    }

    fun openSpeechDialog(view: View) {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.label_speech_search_hint))
            startActivityForResult(this, REQUEST_CODE_SPEECH_INPUT)
        }
    }

    private fun searchGame(searchText: String) {
        val query = searchText
            .toLowerCase(Locale.getDefault())
            .unaccent()

        api.searchGames(query).enqueue(object : Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.message_error_api_request),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.message_error_api_request),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun openCart(view: View) {
        Intent(this, CartActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun CharSequence.unaccent(): String {
        val re = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return re.replace(temp, "")
    }
}