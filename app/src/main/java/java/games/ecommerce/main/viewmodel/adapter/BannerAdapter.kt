package java.games.ecommerce.main.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.banner_card.view.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.utils.loadImg

class BannerAdapter(
    private val bannerList: List<Banner>,
    private val onCLick: (Banner) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellsForRow = layoutInflater.inflate(R.layout.banner_card, parent, false)
        return BannerViewHolder(cellsForRow, onCLick)
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