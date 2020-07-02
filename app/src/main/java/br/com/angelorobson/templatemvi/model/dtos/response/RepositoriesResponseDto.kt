package br.com.angelorobson.templatemvi.model.dtos.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoriesResponseDto(
        val items: List<RepositoryDto>
)

@JsonClass(generateAdapter = true)
data class RepositoryDto(
        val id: Int,
        val name: String,
        val description: String?,
        val forks_count: Int,
        val stargazers_count: Int,
        val owner: OwnerDto
)

@JsonClass(generateAdapter = true)
data class OwnerDto(
        val login: String,
        val avatar_url: String
)
