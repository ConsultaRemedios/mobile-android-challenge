package mazer.arthur.gamingshop.view.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.utils.extensions.inflate
import mazer.arthur.gamingshop.domain.models.GameDetails
import mazer.arthur.gamingshop.utils.listeners.SpotlightClicked

class SpotlightAdapter(private var listener: SpotlightClicked? = null): RecyclerView.Adapter<SpotlightAdapter.ViewHolder>() {

    private var context: Context? = null

    var gameDetailsList: ArrayList<GameDetails> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(inflate(R.layout.item_spotlight,parent))
    }

    override fun getItemCount(): Int {
        return gameDetailsList.size
    }

    override fun onBindViewHolder(holder: SpotlightAdapter.ViewHolder, position: Int) {
        val spotlight = gameDetailsList[position]
        holder.publisher.text = spotlight.publisher
        holder.title.text = spotlight.title
        Picasso.get().load(spotlight.image).into(holder.gamePoster)

        holder.price.text = context?.getString(R.string.original_price_placeholder, spotlight.price.toString().toFloat().toString())
        holder.discount.text =  context?.getString(R.string.price_placeholder, spotlight.discount.toDouble().toString())
        holder.price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener {
            listener?.onSpotlightClicked(spotlight)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var gamePoster: ImageView = view.findViewById(R.id.ivGamePoster)
        var publisher: TextView = view.findViewById(R.id.tvPublisher)
        var title: TextView = view.findViewById(R.id.tvGameName)
        var price: TextView = view.findViewById(R.id.tvOriginalPrice)
        var discount: TextView = view.findViewById(R.id.tvDiscountedPrice)

    }

}