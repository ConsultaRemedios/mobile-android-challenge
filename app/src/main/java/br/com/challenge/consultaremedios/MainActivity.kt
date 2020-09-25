
package br.com.challenge.consultaremedios

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.MobileTestService
import br.com.challenge.consultaremedios.model.Banner
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val carousel: ImageCarousel = findViewById(R.id.banner)
        carousel.onItemClickListener = object: OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                openURL(carouselItem.caption)
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // do nothing
            }
        }
        val api = MobileTestService.buildService(Endpoints::class.java)

        val getBannersCall = api.getBanners()
        getBannersCall.enqueue(object : Callback<List<Banner>> {
            override fun onResponse(call: Call<List<Banner>>, response: Response<List<Banner>>) {
                if (response.isSuccessful) {
                    val list = mutableListOf<CarouselItem>()
                    val banners = response.body()
                    banners?.forEach { list.add(CarouselItem(imageUrl = it.image, it.url)) }
                    carousel.addData(list)
                }
            }

            override fun onFailure(call: Call<List<Banner>>, t: Throwable) {
                Snackbar.make(carousel, "Não foi possíverl bla bla bla", Snackbar.LENGTH_LONG)
            }
        })
    }

    fun openURL(url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}