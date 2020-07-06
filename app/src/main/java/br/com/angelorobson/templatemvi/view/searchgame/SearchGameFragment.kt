package br.com.angelorobson.templatemvi.view.searchgame

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.searchgame.widgets.GameFoundAdapter
import br.com.angelorobson.templatemvi.view.utils.setVisibleOrGone
import com.wanderingcan.persistentsearch.PersistentSearchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_game.*
import java.util.*
import java.util.concurrent.TimeUnit

private val VOICE_RECOGNITION_CODE = 9999

class SearchGameFragment : Fragment(R.layout.fragment_search_game) {

    private val mCompositeDisposable = CompositeDisposable()
    private lateinit var mLayoutManager: LinearLayoutManager
    private var mMicEnabled = false

    private val mSearchTermSubject = PublishSubject.create<String>()
    private var mSearchTerm: String = ""

    override fun onStart() {
        super.onStart()
        mMicEnabled = isIntentAvailable(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH))

        val adapter = GameFoundAdapter()
        setupRecyclerView(adapter)
        initSearchBarListener(mSearchTermSubject)

        val disposable = Observable.mergeArray(
                adapter.gameClicks.map { GameFoundClickedEvent(it) },
                mSearchTermSubject
                        .skip(1)
                        .distinctUntilChanged()
                        .debounce(100, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map {
                            game_search_progress_horizontal.setVisibleOrGone(true)
                            SearchGameByTermEvent(it.toLowerCase(Locale.getDefault()))
                        }
        )
                .compose(getViewModel(SearchGameViewModel::class).init(InitialEvent()))
                .subscribe(
                        { model ->
                            when (model.searchGameResult) {
                                is SearchGameResult.Loading -> {
                                    game_search_progress_horizontal.setVisibleOrGone(model.searchGameResult.isLoading)
                                }
                                is SearchGameResult.GamesFoundByTerm -> {
                                    val spotlights = model.searchGameResult.spotlights
                                    game_search_not_found_text_view.setVisibleOrGone(spotlights.isEmpty())
                                    adapter.submitList(spotlights)
                                    game_search_progress_horizontal.setVisibleOrGone(model.searchGameResult.isLoading)

                                    game_search_search_bar.populateSearchText(mSearchTerm)
                                }
                                is SearchGameResult.Error -> {
                                    game_search_progress_horizontal.setVisibleOrGone(model.searchGameResult.isLoading)
                                }
                            }
                        },
                        {

                        }
                )

        mCompositeDisposable.add(disposable)
    }

    private fun initSearchBarListener(searchTermSubject: PublishSubject<String>) {
        game_search_search_bar.setOnSearchListener(object : PersistentSearchView.OnSearchListener {
            override fun onSearchClosed() {

            }

            override fun onSearch(text: CharSequence?) {

            }

            override fun onSearchCleared() {
                searchTermSubject.onNext("")
                mSearchTerm = ""
            }

            override fun onSearchTermChanged(term: CharSequence?) {
                searchTermSubject.onNext(term.toString())
                mSearchTerm = term.toString().toLowerCase(Locale.getDefault())

            }

            override fun onSearchOpened() {

            }

        })


        game_search_search_bar.setOnIconClickListener(object : PersistentSearchView.OnIconClickListener {
            override fun OnEndIconClick() {
                startVoiceRecognition()
            }

            override fun OnNavigationIconClick() {

            }

        })
    }

    private fun isIntentAvailable(intent: Intent): Boolean {
        val mgr: PackageManager? = activity?.packageManager
        if (mgr != null) {
            val list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            return list.size > 0
        }
        return false
    }

    private fun startVoiceRecognition() {
        if (mMicEnabled) {
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_PROMPT,
                        getString(R.string.speak_now))

                startActivityForResult(this, VOICE_RECOGNITION_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_CODE && resultCode == RESULT_OK) {
            data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let {
                val termVoiceRecognitionReturned = it[0]
                game_search_search_bar.populateSearchText(termVoiceRecognitionReturned)
                mSearchTermSubject.onNext(termVoiceRecognitionReturned)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupRecyclerView(repositoryAdapter: GameFoundAdapter) {
        mLayoutManager = LinearLayoutManager(context)

        search_game_recycler_view.apply {
            layoutManager = mLayoutManager
            adapter = repositoryAdapter
            addItemDecoration(
                    DividerItemDecoration(
                            context,
                            mLayoutManager.orientation
                    )
            )
        }
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }

}