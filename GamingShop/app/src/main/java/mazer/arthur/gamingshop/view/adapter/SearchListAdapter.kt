package mazer.arthur.gamingshop.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.domain.models.GameDetails
import mazer.arthur.gamingshop.utils.extensions.inflate

class SearchListAdapter: RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var context: Context? = null
    var gameList: ArrayList<GameDetails> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    fun addSearchedGames(listOfGames: List<GameDetails>){
        gameList.clear()
        gameList.addAll(listOfGames)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(inflate(R.layout.item_search, parent))
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: SearchListAdapter.ViewHolder, position: Int) {
        val gameDetails = gameList[position]

        holder.title.text = gameDetails.title
        holder.price.text = context?.getString(R.string.price_placeholder, gameDetails.discount.toString())
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tvGameTitleSearch)
        var price: TextView = view.findViewById(R.id.tvGamePriceSearch)

    }
}
