package br.com.angelorobson.templatemvi.view.shoppingcart


import br.com.angelorobson.templatemvi.model.builders.ShoppingCartBuilder
import br.com.angelorobson.templatemvi.view.shoppingcart.ShoppingCartEffect.ObserverShoppingCart
import br.com.angelorobson.templatemvi.view.shoppingcart.ShoppingCartModelResult.ShoppingCartItemsLoaded
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class ShoppingCartViewModelTest {

    private lateinit var updateSpec: UpdateSpec<ShoppingCartModel, ShoppingCartEvent, ShoppingCartEffect>

    @Before
    fun setUp() {
        updateSpec = UpdateSpec(::shoppingCartUpdate)
    }

    @Test
    fun `It should InitialEvent is called then dispatched ObserverShoppingCart`() {
        val model = ShoppingCartModel()

        updateSpec
                .given(model)
                .whenEvent(InitialEvent)
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasEffects(ObserverShoppingCart),
                                hasNoModel()
                        )
                )
    }

    @Test
    fun `It should ShoppingItemsCartLoadedEvent is called then ShoppingCartItemsLoaded model is updated`() {
        val model = ShoppingCartModel()
        val shoppingCart = ShoppingCartBuilder.Builder().oneSpotlight().build()
        val list = listOf(shoppingCart, shoppingCart)

        val totalWithoutDiscount = 0.0
        val totalWithDiscount = 0.0
        val totalQuantity = 0
        val freteValue = 0.0

        updateSpec
                .given(model)
                .whenEvent(ShoppingItemsCartLoadedEvent(
                        shoppingItemsCart = list,
                        totalWithDiscount = totalWithDiscount,
                        totalWithoutDiscount = totalWithoutDiscount,
                        totalQuantity = totalQuantity,
                        freteValue = freteValue
                ))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasModel(
                                        model.copy(
                                                shoppingCartResult = ShoppingCartItemsLoaded(
                                                        shoppingItemsCart = list,
                                                        totalWithDiscount = totalWithDiscount,
                                                        totalWithoutDiscount = totalWithoutDiscount,
                                                        totalQuantity = totalQuantity,
                                                        freteValue = freteValue
                                                )
                                        )
                                ),
                                hasNoEffects()

                        )
                )
    }


    @Test
    fun `It should ShoppingCartExceptionsEvent is called then Error model is updated`() {
        val model = ShoppingCartModel()
        val errorMessage = ""

        updateSpec
                .given(model)
                .whenEvent(ShoppingCartExceptionsEvent(errorMessage))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasModel(
                                        model.copy(
                                                shoppingCartResult = ShoppingCartModelResult.Error(errorMessage)
                                        )
                                ),
                                hasNoEffects()
                        )
                )
    }

    @Test
    fun `It should RemoveButtonItemClicked is called then RemoveButtonItemClickedEffect is dispatched`() {
        val model = ShoppingCartModel()
        val shoppingCart = ShoppingCartBuilder.Builder().oneSpotlight().build()

        updateSpec
                .given(model)
                .whenEvent(RemoveButtonItemClicked(shoppingCart))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasNoModel(),
                                hasEffects(ShoppingCartEffect.RemoveButtonItemClickedEffect(shoppingCart))
                        )
                )
    }

    @Test
    fun `It should AddButtonItemClicked is called then AddButtonItemClickedEffect is dispatched`() {
        val model = ShoppingCartModel()
        val shoppingCart = ShoppingCartBuilder.Builder().oneSpotlight().build()

        updateSpec
                .given(model)
                .whenEvent(AddButtonItemClicked(shoppingCart))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasNoModel(),
                                hasEffects(ShoppingCartEffect.AddButtonItemClickedEffect(shoppingCart))
                        )
                )
    }

    @Test
    fun `It should ClearButtonItemClicked is called then ClearButtonItemClickedEffect is dispatched`() {
        val model = ShoppingCartModel()
        val shoppingCart = ShoppingCartBuilder.Builder().oneSpotlight().build()

        updateSpec
                .given(model)
                .whenEvent(ClearButtonItemClicked(shoppingCart))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasNoModel(),
                                hasEffects(ShoppingCartEffect.ClearButtonItemClickedEffect(shoppingCart))
                        )
                )
    }

    @Test
    fun `It should ButtonPurchaseClickedEvent is called then ButtonPurchaseEffect is dispatched`() {
        val model = ShoppingCartModel()
        val shoppingCart = ShoppingCartBuilder.Builder().oneSpotlight().build()
        val list = listOf(shoppingCart, shoppingCart)
        val total = 0.0

        updateSpec
                .given(model)
                .whenEvent(ButtonPurchaseClickedEvent(list, total))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasNoModel(),
                                hasEffects(ShoppingCartEffect.ButtonPurchaseEffect(list, total))
                        )
                )
    }

    @Test
    fun `It should PurchaseSuccessfully is called then ClearDatabaseEffect is dispatched`() {
        val model = ShoppingCartModel()
        val shoppingCart = ShoppingCartBuilder.Builder().oneSpotlight().build()

        updateSpec
                .given(model)
                .whenEvent(ImageItemClicked(shoppingCart))
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasNoModel(),
                                hasEffects(ShoppingCartEffect.ImageItemClickedEffect(shoppingCart))
                        )
                )
    }

    @Test
    fun `It should ImageItemClicked is called then ImageItemClickedEffect is dispatched`() {
        val model = ShoppingCartModel()

        updateSpec
                .given(model)
                .whenEvent(PurchaseSuccessfully)
                .then(
                        assertThatNext<ShoppingCartModel, ShoppingCartEffect>(
                                hasNoModel(),
                                hasEffects(ShoppingCartEffect.ClearDatabaseEffect)
                        )
                )
    }


}