package br.com.angelorobson.templatemvi.view.repositories

import br.com.angelorobson.templatemvi.model.domains.Repository

sealed class RepositoriesEffect

object ObserveRepositoriesEffect : RepositoriesEffect()
object ObserveRetryEffect : RepositoriesEffect()
data class RepositoryClickedEffect(val repository: Repository) : RepositoriesEffect()