package br.com.angelorobson.templatemvi.view.home

import br.com.angelorobson.templatemvi.model.builders.BannerBuilder.Builder
import br.com.angelorobson.templatemvi.model.builders.SpotlightBuilder
import br.com.angelorobson.templatemvi.view.home.HomeEffect.*
import br.com.angelorobson.templatemvi.view.home.HomeResult.SpotlightsLoaded
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var updateSpec: UpdateSpec<HomeModel, HomeEvent, HomeEffect>

    @Before
    fun setUp() {
        updateSpec = UpdateSpec(::homeUpdate)
    }

    @Test
    fun `It should InitialEvent is called then dispatched observerBanner`() {
        val model = HomeModel()

        updateSpec
                .given(model)
                .whenEvent(InitialEvent)
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasEffects(ObserverBanner)

                        )
                )
    }

    @Test
    fun `It should BannersLoadedEvent is called then update model BannerLoaded and dispatched ObserverSpotlight`() {
        val model = HomeModel()
        val banner = Builder().oneBanner().build()
        val list = listOf(banner, banner, banner)

        updateSpec
                .given(model)
                .whenEvent(BannersLoadedEvent(list))
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasModel(model.copy(
                                        homeResult = HomeResult.BannerLoaded(list)
                                )),
                                hasEffects(ObserverSpotlight)

                        )
                )
    }

    @Test
    fun `It should SpotlightLoadedEvent is called then update model SpotlightsLoaded and dispatched GetItemCountEffect`() {
        val model = HomeModel()
        val spotlight = SpotlightBuilder.Builder().oneSpotlight().build()
        val list = listOf(spotlight, spotlight, spotlight)

        updateSpec
                .given(model)
                .whenEvent(SpotlightLoadedEvent(list))
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasModel(model.copy(
                                        homeResult = SpotlightsLoaded(list))
                                ),
                                hasEffects(GetItemCountEffect)

                        )
                )
    }

    @Test
    fun `It should HomeExceptionEvent is called then update model Error`() {
        val model = HomeModel()
        val errorMessage = "error message"

        updateSpec
                .given(model)
                .whenEvent(HomeExceptionEvent(errorMessage))
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasModel(model.copy(
                                        homeResult = HomeResult.Error(errorMessage))
                                ),
                                hasNoEffects()
                        )
                )
    }

    @Test
    fun `It should GameClickedEvent is called then GameClickedEffect is dispatched`() {
        val model = HomeModel()
        val spotlight = SpotlightBuilder.Builder().oneSpotlight().build()

        updateSpec
                .given(model)
                .whenEvent(GameClickedEvent(spotlight))
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasEffects(GameClickedEffect(spotlight)),
                                hasNoModel()
                        )
                )
    }

    @Test
    fun `It should SearchViewClickedEvent is called then SearchViewClickedEffect is dispatched`() {
        val model = HomeModel()

        updateSpec
                .given(model)
                .whenEvent(SearchViewClickedEvent)
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasEffects(SearchViewClickedEffect),
                                hasNoModel()
                        )
                )
    }

    @Test
    fun `It should BannerClickedEvent is called then BannerClickedEffect is dispatched`() {
        val model = HomeModel()
        val url = "url"

        updateSpec
                .given(model)
                .whenEvent(BannerClickedEvent(url))
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasEffects(BannerClickedEffect(url)),
                                hasNoModel()
                        )
                )
    }

    @Test
    fun `It should CartActionButtonClickedEvent is called then CartActionButtonClickedEffect is dispatched`() {
        val model = HomeModel()

        updateSpec
                .given(model)
                .whenEvent(CartActionButtonClickedEvent)
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasEffects(CartActionButtonClickedEffect),
                                hasNoModel()
                        )
                )
    }

    @Test
    fun `It should GetItemsCartCountEvent is called then model ShoppingCartItemCount is updated`() {
        val model = HomeModel()
        val count = 2

        updateSpec
                .given(model)
                .whenEvent(GetItemsCartCountEvent(count))
                .then(
                        assertThatNext<HomeModel, HomeEffect>(
                                hasModel(
                                        model.copy(
                                                homeResult = HomeResult.ShoppingCartItemCount(count)
                                        )
                                ),
                                hasNoEffects()
                        )
                )
    }

}