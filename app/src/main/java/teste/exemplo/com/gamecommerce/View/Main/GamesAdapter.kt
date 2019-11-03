package teste.exemplo.com.gamecommerce.View.Main

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.reactivex.annotations.NonNull
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.R
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney


class GamesAdapter(private var context: Context) : RecyclerView.Adapter<GamesAdapter.ViewHolder>(), IGamesAdapterView  {

    private var game: Game = Game()
    private var viewHolder: ViewHolder? = null

    var onItemClick: ((Game) -> Unit)? = null


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Cache.getGames().size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        viewHolder!!.setIsRecyclable(false)
        game = Cache.getGames()[position]

        setHolderTexts()
        setImage()
    }

    override fun setHolderTexts() {
        viewHolder!!.gameName.text = game.name
        viewHolder!!.platformName.text = game.platform
        viewHolder!!.gamePrice.text = formatMoney(game.price)
    }

    override fun setImage() {
        Glide.with(context).load(game.image).into(viewHolder!!.gameImageView)
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gameImageView: ImageView = itemView.findViewById(R.id.gameImageView)
        val gameName: TextView = itemView.findViewById(R.id.game_name)
        val platformName: TextView = itemView.findViewById(R.id.platform_name)
        val gamePrice: TextView = itemView.findViewById(R.id.game_price)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Cache.getGames()[adapterPosition])
            }
        }
    }
}