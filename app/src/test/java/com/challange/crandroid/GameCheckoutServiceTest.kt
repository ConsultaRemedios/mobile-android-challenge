package com.challange.crandroid

import com.challange.crandroid.api.GameCheckoutServiceInitializer
import com.challange.crandroid.data.request.Checkout
import com.challange.crandroid.data.request.EnderecoEntrega
import org.junit.Assert
import org.junit.Test

class GameCheckoutServiceTest {

    @Test
    fun assetGetGames() {
        val it = GameCheckoutServiceInitializer().gameCheckoutService().getGames().execute()
        Assert.assertTrue(it.isSuccessful)
        Assert.assertNotNull(it.body())
    }

    @Test
    fun assertGetGameDetails() {
        var it =
            GameCheckoutServiceInitializer().gameCheckoutService().getGameDetails(102).execute()
        Assert.assertTrue(it.isSuccessful)
        Assert.assertNotNull(it.body())

        it = GameCheckoutServiceInitializer().gameCheckoutService().getGameDetails(999).execute()
        Assert.assertTrue(it.isSuccessful)
        Assert.assertNull(it.body())
    }

    @Test
    fun assertCheckout() {
        val enderecoEntrega = EnderecoEntrega(
            "80510-342",
            "Rua Desembargador Vieira Cavalcanti",
            "703",
            "Curitiba",
            "Paraná",
            "Brasil"
        )

        val checkout = Checkout(emptyList(), enderecoEntrega, 0.00)
        val it =
            GameCheckoutServiceInitializer().gameCheckoutService().checkout(checkout!!).execute()
        Assert.assertTrue(it.isSuccessful)
    }
}