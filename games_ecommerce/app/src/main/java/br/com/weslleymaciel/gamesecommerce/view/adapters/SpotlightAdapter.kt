package br.com.weslleymaciel.gamesecommerce.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.common.utils.loadImage
import br.com.weslleymaciel.gamesecommerce.common.utils.numberToPrice
import org.jetbrains.anko.find
import java.lang.String

class SpotlightAdapter(private val items: List<Game>, val onClick: (gameId: Number) -> Unit) : RecyclerView.Adapter<SpotlightAdapter.SpotlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightViewHolder {
        return SpotlightViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_spotlight_item, parent, false), onClick)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: SpotlightViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class SpotlightViewHolder(view: View, val onClick: (physicalCardId: Number) -> Unit) : RecyclerView.ViewHolder(view) {

        private val view = view
        private val ivImage = view.find<ImageView>(R.id.ivGame)
        private val cvGamePhoto = view.find<CardView>(R.id.cvGamePhoto)
        private val tvPublisher = view.find<TextView>(R.id.tvPublisher)
        private val tvTitle = view.find<TextView>(R.id.tvTitle)
        private val tvDiscount = view.find<TextView>(R.id.tvDiscount)
        private val tvPrice = view.find<TextView>(R.id.tvPrice)

        fun bind(game: Game) {
            view.setOnClickListener {
                onClick(game.id)
            }

            ivImage.loadImage(game.image!!, R.drawable.placeholder)
            tvPublisher.text = game.publisher
            tvTitle.text = game.title

            if (game.discount.toFloat() < game.price.toFloat()){
                tvPrice.visibility = View.VISIBLE
                tvPrice.text = String.format(view.resources.getString(R.string.discount), numberToPrice(game.price))
                tvDiscount.text = numberToPrice(game.discount)
            }else{
                tvPrice.visibility = View.GONE
                tvDiscount.text = numberToPrice(game.price)
            }

        }
    }
}