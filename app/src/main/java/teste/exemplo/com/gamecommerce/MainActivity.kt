package teste.exemplo.com.gamecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_game)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.game_super_mario_odyssey))
        imageList.add(SlideModel(R.drawable.game_super_mario_odyssey))
        imageList.add(SlideModel(R.drawable.game_super_mario_odyssey))
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
}
