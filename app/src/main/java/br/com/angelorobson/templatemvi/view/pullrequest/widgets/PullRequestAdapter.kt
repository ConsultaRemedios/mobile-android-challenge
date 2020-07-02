package br.com.angelorobson.templatemvi.view.pullrequest.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.PullRequestRowBinding
import br.com.angelorobson.templatemvi.model.domains.PullRequest
import br.com.angelorobson.templatemvi.view.utils.DiffUtilCallback
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer

class PullRequestAdapter : ListAdapter<PullRequest, PullRequestAdapter.PullRequestViewHolder>(DiffUtilCallback<PullRequest>()) {


    private val pullRequestClicksSubject = PublishSubject.create<Int>()
    val pullRequestClicks: Observable<PullRequest> = pullRequestClicksSubject.map { position -> getItem(position) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding = DataBindingUtil.bind<PullRequestRowBinding>(
                LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                )
        )

        return PullRequestViewHolder(binding?.root!!, binding, pullRequestClicksSubject)
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.pull_request_row
    }


    class PullRequestViewHolder(
            override val containerView: View,
            private val binding: PullRequestRowBinding?,
            private val repositoryClicks: PublishSubject<Int>
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(pullRequest: PullRequest) {
            binding?.apply {
                item = pullRequest
                pullRequestConstraintLayout.clicks().map { adapterPosition }.subscribe(repositoryClicks)
                executePendingBindings()
            }
        }

    }

}
