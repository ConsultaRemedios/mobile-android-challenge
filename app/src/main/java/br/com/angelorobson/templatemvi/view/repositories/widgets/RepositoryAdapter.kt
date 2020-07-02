package br.com.angelorobson.templatemvi.view.repositories.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.RepositoryRowBinding
import br.com.angelorobson.templatemvi.model.domains.Repository
import br.com.angelorobson.templatemvi.view.utils.DiffUtilCallback
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer

class RepositoryAdapter : PagedListAdapter<Repository, RepositoryViewHolder>(DiffUtilCallback<Repository>()) {

    private val repositoryClicksSubject = PublishSubject.create<Int>()
    val repositoryClicks: Observable<Repository> = repositoryClicksSubject.map { position -> getItem(position) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                viewType,
                parent,
                false
        )

        val binding = DataBindingUtil.bind<RepositoryRowBinding>(view)

        return RepositoryViewHolder(view, binding, repositoryClicksSubject)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun getItemViewType(position: Int) = R.layout.repository_row



}

class RepositoryViewHolder(
        override val containerView: View,
        private val binding: RepositoryRowBinding?,
        private val repositoryClicksSubject: PublishSubject<Int>
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(repository: Repository) {
        binding?.apply {
            item = repository
            repositoryRowConstraintLayout.clicks().map { adapterPosition }.subscribe(repositoryClicksSubject)
            executePendingBindings()
        }
    }
}

