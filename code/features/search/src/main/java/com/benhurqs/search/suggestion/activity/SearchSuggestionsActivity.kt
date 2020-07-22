package com.benhurqs.search.suggestion.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benhurqs.base.actions.Actions
import com.benhurqs.base.utils.Utils
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Banner
import com.benhurqs.network.entities.Spotlight
import com.benhurqs.network.entities.Suggestion
import com.benhurqs.search.R
import com.benhurqs.search.suggestion.adapter.SuggestionAdapter
import kotlinx.android.synthetic.main.search_suggestion_activity.*

class SearchSuggestionsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_suggestion_activity)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews(){
        search_edittext.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                var query = p0.toString()
                if(Utils.isEmpty(query)){
                    search_suggestion_list.visibility = View.GONE
                }else{
                    callAPI(p0.toString())
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun managerRecyclerView(list: List<Suggestion>?){
        search_suggestion_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_suggestion_list.adapter = SuggestionAdapter(list){ spotlight ->
            startActivity(Actions.detailIntent(this, spotlight.id))
        }
    }


    private fun callAPI(query: String?){
        NetworkRepository.getSuggestion(
            query = query,
            onStart = { onLoading() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onLoading() {
        Log.e("Search", "Start")
    }

    private fun onSuccess(list: List<Suggestion>?) {
        Log.e("Search", "Success")

        search_suggestion_list.visibility = View.VISIBLE
        managerRecyclerView(list)
    }

    private fun onFailure(error: String?){
        Log.e("Search", "Error")
    }

    private fun onFinish(){
        Log.e("Search", "Finishing")
    }

}