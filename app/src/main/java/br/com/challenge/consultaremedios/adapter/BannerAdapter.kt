package br.com.challenge.consultaremedios.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import br.com.challenge.consultaremedios.R
import br.com.challenge.consultaremedios.model.Banner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BannerAdapter(context: Context,
                    private val banners: List<Banner>,
                    private val bannerTapListener: BannerTapListener
): PagerAdapter() {

    private val mContext = context

    override fun getCount(): Int {
        return banners.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(R.layout.banner_item, container, false)

        val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
        view.findViewById<ImageView>(R.id.banner_image).apply {
            Glide.with(mContext)
                .load(banners[position].image)
                .apply(requestOptions)
                .into(this)
        }

        view.setOnClickListener {
            bannerTapListener.onBannerTap(banners[position].url)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    interface BannerTapListener { fun onBannerTap(url: String) }
}