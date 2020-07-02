package br.com.angelorobson.templatemvi.view.pullrequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.pullrequest.widgets.PullRequestAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_pull_request.*


class PullRequestFragment : Fragment(R.layout.fragment_pull_request) {

    private val args: PullRequestFragmentArgs by navArgs()

    private val mCompositeDisposable = CompositeDisposable()
    private lateinit var mLayoutManager: LinearLayoutManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PullRequestAdapter()

        setupRecyclerView(adapter)

        val disposable = Observable.mergeArray(
                adapter.pullRequestClicks.map { PullRequestClickedEvent(it) }
        )
                .compose(getViewModel(PullRequestViewModel::class).init(InitialEvent(args.repository)))
                .subscribe(
                        { model ->
                            handleResult(model, adapter)
                        },
                        {
                        }
                )

        mCompositeDisposable.add(disposable)

    }

    private fun setupRecyclerView(pullRequestAdapter: PullRequestAdapter) {
        mLayoutManager = LinearLayoutManager(context)

        pull_request_recycler_view.apply {
            layoutManager = mLayoutManager
            adapter = pullRequestAdapter
            addItemDecoration(
                    DividerItemDecoration(
                            context,
                            mLayoutManager.orientation
                    )
            )
        }
    }

    private fun handleResult(model: PullRequestModel, adapter: PullRequestAdapter) {
        when (model.pullRequestResult) {
            is PullRequestResult.Loading -> {
                pull_request_progress_horizontal.visibility = View.VISIBLE
            }

            is PullRequestResult.PullRequestLoaded -> {
                pull_request_progress_horizontal.visibility = if (model.pullRequestResult.isLoading) View.VISIBLE else View.GONE
                adapter.submitList(model.pullRequestResult.pullRequests)
            }

            is PullRequestResult.Error -> {
                pull_request_progress_horizontal.visibility = if (model.pullRequestResult.isLoading) View.VISIBLE else View.GONE
            }

        }
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }

}
