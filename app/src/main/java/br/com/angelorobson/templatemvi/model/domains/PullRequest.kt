package br.com.angelorobson.templatemvi.model.domains

import java.util.*

data class PullRequest(
        val title: String,
        val description: String,
        val user: User,
        val htmlUrl: String,
        val createdAt: Date
)