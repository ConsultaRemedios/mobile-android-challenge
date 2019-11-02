package teste.exemplo.com.gamecommerce.View.Main

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.reactivex.annotations.NonNull
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.R
import teste.exemplo.com.gamecommerce.Util.Cache




class GamesAdapter(var context: Context) : RecyclerView.Adapter<GamesAdapter.ViewHolder>(), IGamesAdapterView  {

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
        Log.d("JOGO",Cache.getGames()[0].image)
        game = Cache.getGames()[position]

        setHolderTexts()
        setImage()
    }

    fun setHolderTexts() {
        viewHolder!!.game_name.text = game.name
        viewHolder!!.platform_name.text = game.platform
        viewHolder!!.game_price.text = game.price.toString()
    }

    fun setImage() {
        Glide.with(context).load(game.image).into(viewHolder!!.gameImageView)
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gameImageView: ImageView
        val game_name: TextView
        val platform_name: TextView
        val game_price: TextView

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Cache.getGames()[adapterPosition])
            }
            gameImageView = itemView.findViewById(R.id.gameImageView)
            platform_name = itemView.findViewById(R.id.platform_name)
            game_name = itemView.findViewById(R.id.game_name)
            game_price = itemView.findViewById(R.id.game_price)
        }
    }
}