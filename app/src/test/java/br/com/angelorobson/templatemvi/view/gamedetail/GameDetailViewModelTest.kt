package br.com.angelorobson.templatemvi.view.gamedetail

import br.com.angelorobson.templatemvi.model.builders.SpotlightBuilder
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class GameDetailViewModelTest {

    private lateinit var updateSpec: UpdateSpec<GameDetailModel, GameDetailEvent, GameDetailEffect>

    @Before
    fun setUp() {
        updateSpec = UpdateSpec(::gameUpdate)
    }

    @Test
    fun `It should InitialEvent is called then dispatched ObservableGameEffect`() {
        val model = GameDetailModel()
        val id = 1

        updateSpec
                .given(model)
                .whenEvent(InitialEvent(id))
                .then(
                        assertThatNext<GameDetailModel, GameDetailEffect>(
                                hasEffects(ObservableGameEffect(id)),
                                hasNoModel()
                        )
                )
    }

    @Test
    fun `It should GameDetailExceptionEvent is called then Error model is updated`() {
        val model = GameDetailModel()
        val errorMessage = "error"

        updateSpec
                .given(model)
                .whenEvent(GameDetailExceptionEvent(errorMessage))
                .then(
                        assertThatNext<GameDetailModel, GameDetailEffect>(
                                hasModel(
                                        model.copy(
                                                gameDetailResult = GameDetailResult.Error(errorMessage)
                                        )
                                ),
                                hasNoEffects()

                        )
                )
    }

    @Test
    fun `It should GameLoadedEvent is called then GameLoaded model is updated`() {
        val model = GameDetailModel()
        val errorMessage = "error"
        val spotlight = SpotlightBuilder.Builder().build()

        updateSpec
                .given(model)
                .whenEvent(GameLoadedEvent(spotlight))
                .then(
                        assertThatNext<GameDetailModel, GameDetailEffect>(
                                hasModel(
                                        model.copy(
                                                gameDetailResult = GameDetailResult.GameLoaded(spotlight)
                                        )
                                ),
                                hasEffects(GetItemCartEffect(spotlight))

                        )
                )
    }

    @Test
    fun `It should AddOrRemoveItemCardEvent is called thenAddOrRemoveItemCardEffect is dispatched`() {
        val model = GameDetailModel()
        val spotlight = SpotlightBuilder.Builder().build()

        updateSpec
                .given(model)
                .whenEvent(AddOrRemoveItemCardEvent(spotlight))
                .then(
                        assertThatNext<GameDetailModel, GameDetailEffect>(
                                hasNoModel(),
                                hasEffects(AddOrRemoveItemCardEffect(spotlight))

                        )
                )
    }

    @Test
    fun `It should StatusShoppingCartItemEvent is called ItemCartStatusResult model is updated`() {
        val model = GameDetailModel()
        val isCartItemAdded = true

        updateSpec
                .given(model)
                .whenEvent(StatusShoppingCartItemEvent(isCartItemAdded))
                .then(
                        assertThatNext<GameDetailModel, GameDetailEffect>(
                                hasModel(
                                        model.copy(
                                                gameDetailResult = GameDetailResult.ItemCartStatusResult(isCartItemAdded)
                                        )
                                ),
                                hasNoEffects()

                        )
                )
    }


}