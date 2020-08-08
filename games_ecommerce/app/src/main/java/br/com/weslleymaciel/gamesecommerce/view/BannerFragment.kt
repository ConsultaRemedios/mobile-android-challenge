package br.com.weslleymaciel.gamesecommerce.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.Banner
import br.com.weslleymaciel.gamesecommerce.common.utils.loadImage
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_banner.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class BannerFragment(val banner: Banner) : Fragment(R.layout.fragment_banner) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBanner.loadImage(banner.image!!, R.drawable.placeholder)

        view.onClick {
            activity!!.startActivity<WebViewActivity>("URL" to banner.url)
        }
    }
}