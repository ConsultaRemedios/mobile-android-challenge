package br.com.angelorobson.templatemvi.model.repositories

import br.com.angelorobson.templatemvi.model.domains.PullRequest
import br.com.angelorobson.templatemvi.model.domains.User
import br.com.angelorobson.templatemvi.model.dtos.response.PullRequestsDto
import br.com.angelorobson.templatemvi.model.services.PullRequestService
import io.reactivex.Observable
import javax.inject.Inject

class PullRequestRepository @Inject constructor(
        private val service: PullRequestService
) {

    fun getAll(owner: String, repositoryName: String): Observable<List<PullRequest>> {
        return service.getAll(owner, repositoryName)
                .map { response ->
                    response.map {
                        mapDtoToDomain(it)
                    }
                }
    }
}

fun mapDtoToDomain(dto: PullRequestsDto): PullRequest {
    return PullRequest(
            title = dto.title,
            description = dto.body,
            user = User(
                    login = dto.user.login,
                    avatarUrl = dto.user.avatar_url
            ),
            createdAt = dto.created_at,
            htmlUrl = dto.html_url
    )
}