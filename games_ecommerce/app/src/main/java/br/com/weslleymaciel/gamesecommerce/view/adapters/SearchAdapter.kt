package br.com.weslleymaciel.gamesecommerce.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import br.com.weslleymaciel.gamesecommerce.MainActivity
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.SearchGame
import br.com.weslleymaciel.gamesecommerce.common.utils.numberToPrice
import br.com.weslleymaciel.gamesecommerce.data.WebServiceFactory
import br.com.weslleymaciel.gamesecommerce.viewmodel.GamesViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SearchAdapter(): BaseAdapter(), Filterable, AutoCompleteTextView.Validator {
    private var context: Context? = null
    private var resultList: List<SearchGame> = ArrayList<SearchGame>()
    private var selected: SearchGame? = null

    constructor(ctx: Context?) : this() {
        context = ctx
    }

    fun getSelected(position: Int): SearchGame? {
        return resultList[position]
    }

    override fun getCount(): Int {
        return resultList.size
    }

    override fun getItem(index: Int): SearchGame? {
        return resultList[index]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.search_item, parent, false)
        }
        var strTitle: String? = getItem(position)!!.title
        var strPrice: String? = numberToPrice(getItem(position)!!.price.toFloat() - getItem(position)!!.discount.toFloat())

        (convertView!!.findViewById<View>(R.id.tvTitle) as TextView).text = strTitle
        (convertView!!.findViewById<View>(R.id.tvPrice) as TextView).text = strPrice

        return convertView
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            var term = ""
            private val service = WebServiceFactory.create()

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint != null) {
                    term = constraint.toString().toLowerCase()
                    service.searchGame(term)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ data ->
                            data?.let {
                                val filterResults = Filter.FilterResults()
                                filterResults.values = it
                                filterResults.count = it.size
                                publishResults(term!!, filterResults)
                            } ?: run {

                            }
                        }, { error ->
                            Toast.makeText(MainActivity.ctx, "teste", Toast.LENGTH_SHORT).show()
                        })

                } else {
                    fixText(null)
                }
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                if (results != null && results.count > 0) {
                    resultList = results.values as List<SearchGame>
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    override fun isValid(text: CharSequence): Boolean {
        for (game in resultList) {
            if (game.title == text.toString()) {
                selected = game
                return true
            }
        }
        return false
    }

    override fun fixText(invalidText: CharSequence?): CharSequence? {
        selected = null
        return null
    }
}