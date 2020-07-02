package br.com.angelorobson.templatemvi.view.pullrequest

import br.com.angelorobson.templatemvi.model.domains.PullRequest
import br.com.angelorobson.templatemvi.model.domains.Repository

sealed class PullRequestEvent

data class InitialEvent(val repository: Repository) : PullRequestEvent()

data class PullRequestLoadedEvent(val pullRequests: List<PullRequest>) : PullRequestEvent()

data class PullRequestClickedEvent(val pullRequest: PullRequest) : PullRequestEvent()

data class PullRequestExceptionEvent(val errorMessage: String) : PullRequestEvent()
