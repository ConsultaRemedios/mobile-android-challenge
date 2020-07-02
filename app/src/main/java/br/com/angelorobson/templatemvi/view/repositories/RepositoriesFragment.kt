package br.com.angelorobson.templatemvi.view.repositories

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.repositories.widgets.RepositoryAdapter
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_repositories.*


class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {


    private val mCompositeDisposable = CompositeDisposable()
    private var mCompositeDisposablePaged: CompositeDisposable? = null
    private lateinit var mLayoutManager: LinearLayoutManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(view)
        println(savedInstanceState)
        val adapter = RepositoryAdapter()

        setupRecyclerView(adapter)
        val disposable = Observable
                .mergeArray(
                        adapter.repositoryClicks.map { RepositoryClickedEvent(it) },
                        repository_try_again_button.clicks().map {
                            repository_try_again_button.visibility = GONE
                            RetryEvent
                        }
                )
                .compose(getViewModel(RepositoriesViewModel::class).init(InitialEvent))
                .subscribe(
                        { model ->
                            when (model.repositoryResult) {
                                is RepositoriesResult.RepositoriesLoaded -> {
                                    mCompositeDisposablePaged = model.repositoryResult.compositeDisposable
                                    adapter.submitList(model.repositoryResult.repositories)
                                    hideOrVisibleProgressBar(model.repositoryResult.isLoading)

                                    if (model.repositoryResult.hasError) repository_try_again_button.visibility = VISIBLE else repository_try_again_button.visibility = GONE
                                }
                            }
                        },
                        {
                            hideOrVisibleProgressBar(false)
                        }
                )

        mCompositeDisposable.add(disposable)
    }

    private fun hideOrVisibleProgressBar(isVisible: Boolean) {
        repository_progress_horizontal?.apply {
            visibility = if (isVisible) VISIBLE else GONE
        }
    }

    private fun setupRecyclerView(repositoryAdapter: RepositoryAdapter) {
        mLayoutManager = LinearLayoutManager(context)

        repositories_recycler_view.apply {
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
        mCompositeDisposablePaged?.clear()
        super.onDestroy()
    }

}
