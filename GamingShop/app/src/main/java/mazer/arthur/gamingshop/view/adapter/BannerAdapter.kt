package mazer.arthur.gamingshop.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.extensions.inflate
import mazer.arthur.gamingshop.models.Banner
import mazer.arthur.gamingshop.view.listeners.BannerClickListener

class BannerAdapter(private var listener: BannerClickListener? = null): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {


    var bannerList: ArrayList<Banner> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.ViewHolder {
        return ViewHolder(inflate(R.layout.item_game_banner,parent))
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: BannerAdapter.ViewHolder, position: Int) {
        val banner = bannerList[position]
        Picasso.get().load(banner.image).into(holder.thumbnail)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView = view.findViewById(R.id.ivGameBanner)
    }

}