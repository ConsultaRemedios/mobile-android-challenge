package com.benhurqs.search.suggestion.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benhurqs.base.actions.Actions
import com.benhurqs.network.entities.Suggestion
import com.benhurqs.search.R
import com.benhurqs.search.suggestion.adapter.SuggestionAdapter
import com.benhurqs.search.suggestion.contract.SuggestionContract
import com.benhurqs.search.suggestion.presenter.SuggestionPresenter
import kotlinx.android.synthetic.main.search_suggestion_activity.*

class SearchSuggestionsActivity : AppCompatActivity(), SuggestionContract.View{

    var presenter: SuggestionContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_suggestion_activity)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews(){
        presenter = SuggestionPresenter(this)

        search_edittext.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                var query = p0.toString()
                presenter?.callAPI(query)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }


    override fun loadSuggestion(list: List<Suggestion>) {
        search_suggestion_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_suggestion_list.adapter = SuggestionAdapter(list){ spotlight ->
            startActivity(Actions.detailIntent(this, spotlight.id))
        }
    }

    override fun showLoading() {
        search_suggestion_progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        search_suggestion_progress.visibility = View.GONE
    }

    override fun hideContent() {
        search_suggestion_list.visibility = View.GONE
    }

    override fun showContent() {
        search_suggestion_list.visibility = View.VISIBLE
    }
}