package br.com.angelorobson.templatemvi.view.searchgame

import br.com.angelorobson.templatemvi.model.builders.SpotlightBuilder
import br.com.angelorobson.templatemvi.view.searchgame.SearchGameEffect.ObserverSpotlightByTermEffect
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import org.junit.Before
import org.junit.Test

class SearchGameViewModelTest {

    private lateinit var updateSpec: UpdateSpec<SearchGameModel, SearchGameEvent, SearchGameEffect>

    @Before
    fun setUp() {
        updateSpec = UpdateSpec(::searchGameUpdate)
    }

    @Test
    fun `It should InitialEvent is called then dispatched ObserverSpotlightByTermEffect`() {
        val model = SearchGameModel()
        val term = ""

        updateSpec
                .given(model)
                .whenEvent(InitialEvent(term))
                .then(
                        UpdateSpec.assertThatNext<SearchGameModel, SearchGameEffect>(
                                hasEffects(ObserverSpotlightByTermEffect(term))

                        )
                )
    }

    @Test
    fun `It should GamesFoundEvent is called then GamesFoundByTerm model is updated`() {
        val model = SearchGameModel()
        val spotligth = SpotlightBuilder.Builder().oneSpotlight().build()
        val list = listOf(spotligth, spotligth)

        updateSpec
                .given(model)
                .whenEvent(GamesFoundEvent(list))
                .then(
                        UpdateSpec.assertThatNext<SearchGameModel, SearchGameEffect>(
                                hasModel(
                                        model.copy(
                                                searchGameResult = SearchGameResult.GamesFoundByTerm(
                                                        spotlights = list
                                                )
                                        )
                                ),
                                hasNoEffects()

                        )
                )
    }


    @Test
    fun `It should SearchGameByTermEvent is called then ObserverSpotlightByTermEffect is dispatched`() {
        val model = SearchGameModel()
        val term = ""

        updateSpec
                .given(model)
                .whenEvent(SearchGameByTermEvent(term))
                .then(
                        UpdateSpec.assertThatNext<SearchGameModel, SearchGameEffect>(
                                hasNoModel(),
                                hasEffects(ObserverSpotlightByTermEffect(term))

                        )
                )
    }

    @Test
    fun `It should SearchGameExceptionEvent is called then Error model is updated`() {
        val model = SearchGameModel()
        val errorMessage = ""

        updateSpec
                .given(model)
                .whenEvent(SearchGameExceptionEvent(errorMessage))
                .then(
                        UpdateSpec.assertThatNext<SearchGameModel, SearchGameEffect>(
                                hasModel(
                                        model.copy(
                                                searchGameResult = SearchGameResult.Error(errorMessage)
                                        )
                                ),
                                hasNoEffects()

                        )
                )
    }

    @Test
    fun `It should GameFoundClickedEvent is called then GameClickedEffect is dispatched`() {
        val model = SearchGameModel()
        val spotlight = SpotlightBuilder.Builder().oneSpotlight().build()

        updateSpec
                .given(model)
                .whenEvent(GameFoundClickedEvent(spotlight))
                .then(
                        UpdateSpec.assertThatNext<SearchGameModel, SearchGameEffect>(
                                hasNoModel(),
                                hasEffects(SearchGameEffect.GameClickedEffect(spotlight))

                        )
                )
    }


}