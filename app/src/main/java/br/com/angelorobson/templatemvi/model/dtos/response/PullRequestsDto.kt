package br.com.angelorobson.templatemvi.model.dtos.response

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class PullRequestsDto(
        val title: String,
        val body: String,
        val user: OwnerDto,
        val html_url: String,
        val created_at: Date
)