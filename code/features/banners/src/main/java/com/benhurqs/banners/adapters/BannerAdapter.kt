package com.benhurqs.banners.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.banners.R
import com.benhurqs.base.adapter.DefaultViewHolder
import com.benhurqs.base.utils.ImageUtils
import com.benhurqs.network.entities.Banner
import kotlinx.android.synthetic.main.banner_card_item.view.*

class BannerAdapter(private val list: List<Banner>?, val onClickItem: (banner: Banner?) -> Unit) : RecyclerView.Adapter<DefaultViewHolder>(){

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefaultViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.banner_card_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        var item = list?.get(position)
        ImageUtils.loadImage(holder.itemView.banner_item_image, item?.image)

        holder.itemView.setOnClickListener {
            onClickItem(item)
        }
    }
}