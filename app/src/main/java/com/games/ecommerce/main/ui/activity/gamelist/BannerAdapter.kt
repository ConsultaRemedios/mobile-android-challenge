package com.games.ecommerce.main.ui.activity.gamelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.Banner
import com.games.ecommerce.utils.loadImg
import kotlinx.android.synthetic.main.card_banner.view.*

class BannerAdapter(
    private val bannerList: List<Banner>,
    private val onCLick: (Banner) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellsForRow = layoutInflater.inflate(R.layout.card_banner, parent, false)
        return BannerViewHolder(
            cellsForRow,
            onCLick
        )
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BannerViewHolder).bind(bannerList[position])
    }
}

class BannerViewHolder(private val view: View, private val onClick: (Banner) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bind(banner: Banner) {
        itemView.apply {
            bannerImage.loadImg(banner.image)
            banner_card.setOnClickListener {
                onClick(banner)
            }
        }
    }
}