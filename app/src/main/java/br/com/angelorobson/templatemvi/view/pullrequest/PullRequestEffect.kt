package br.com.angelorobson.templatemvi.view.pullrequest

import br.com.angelorobson.templatemvi.model.domains.PullRequest
import br.com.angelorobson.templatemvi.model.domains.Repository

sealed class PullRequestEffect

data class ObservePullRequestEffect(val repository: Repository) : PullRequestEffect()
data class PullRequestClickedEffect(val pullRequest: PullRequest) : PullRequestEffect()
