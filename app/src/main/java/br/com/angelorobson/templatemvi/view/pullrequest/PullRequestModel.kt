package br.com.angelorobson.templatemvi.view.pullrequest

import br.com.angelorobson.templatemvi.model.domains.PullRequest

data class PullRequestModel(
        val pullRequestResult: PullRequestResult = PullRequestResult.Loading
)

sealed class PullRequestResult {
    data class Error(
            val errorMessage: String,
            val isLoading: Boolean = false
    ) : PullRequestResult()

    object Loading : PullRequestResult()

    data class PullRequestLoaded(
            val pullRequests: List<PullRequest> = listOf(),
            val isLoading: Boolean = false
    ) : PullRequestResult()
}