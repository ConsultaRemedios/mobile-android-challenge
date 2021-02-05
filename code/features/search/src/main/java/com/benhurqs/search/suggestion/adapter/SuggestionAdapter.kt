package com.benhurqs.search.suggestion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.base.adapter.DefaultViewHolder
import com.benhurqs.base.utils.Utils
import com.benhurqs.network.entities.Suggestion
import com.benhurqs.search.R
import kotlinx.android.synthetic.main.suggestion_item.view.*

class SuggestionAdapter (private val list: List<Suggestion>?, val onClickItem: (suggestion: Suggestion) -> Unit) : RecyclerView.Adapter<DefaultViewHolder>(){

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefaultViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.suggestion_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val item = list?.get(position)

        holder.itemView.search_spotlight_title.text = item?.title
        holder.itemView.search_spotlight_price.text = Utils.formatPrice((item!!.price-item.discount))

        holder.itemView.setOnClickListener {
            onClickItem(item)
        }

        if(position >= list!!.size-1){
            holder.itemView.search_suggestion_line.visibility = View.GONE
        }else{
            holder.itemView.search_suggestion_line.visibility = View.VISIBLE
        }
    }
}