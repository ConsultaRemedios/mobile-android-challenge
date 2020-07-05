package br.com.angelorobson.templatemvi.view.home

import br.com.angelorobson.templatemvi.model.domains.Banner
import br.com.angelorobson.templatemvi.model.domains.Spotlight

sealed class HomeEvent

object InitialEvent : HomeEvent()

data class BannersLoadedEvent(val banners: List<Banner>,
                              val isLoading: Boolean = false) : HomeEvent()

data class GameClickedEvent(val spotlight: Spotlight) : HomeEvent()
object SearchViewClickedEvent : HomeEvent()

data class HomeExceptionEvent(val errorMessage: String) : HomeEvent()

data class SpotlightLoadedEvent(val spotlights: List<Spotlight>,
                                val isLoading: Boolean = false) : HomeEvent()