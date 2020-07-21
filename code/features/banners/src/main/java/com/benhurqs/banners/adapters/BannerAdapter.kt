package com.benhurqs.banners.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.banners.R
import com.benhurqs.base.adapter.DefaultViewHolder
import com.benhurqs.network.entities.Banner

class BannerAdapter(val list: List<Banner>?, val onClickItem: (banner: Banner) -> Unit) : RecyclerView.Adapter<DefaultViewHolder>(){

    override fun getItemCount(): Int {
        return 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefaultViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.banner_card_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickItem(Banner())
        }
    }
}