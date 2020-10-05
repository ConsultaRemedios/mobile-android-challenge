package br.com.challenge.consultaremedios

import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.ApiService
import br.com.challenge.consultaremedios.db.entity.CartItem
import org.junit.Assert
import org.junit.Test

class ApiTest {

    @Test
    fun getBannerTest() {
        val request = ApiService.buildService(Endpoints::class.java)
        val response = request.getBanners().execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun getSpotlightTest() {
        val request = ApiService.buildService(Endpoints::class.java)
        val response = request.getSpotlight().execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun getGameDetailsTest() {
        val request = ApiService.buildService(Endpoints::class.java)
        val response = request.getGameDetails(1).execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun searchGamesTest() {
        val request = ApiService.buildService(Endpoints::class.java)
        val response = request.searchGames("zelda").execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun postCheckoutTest() {
        val items: MutableList<CartItem> = ArrayList()
        items.add(
            CartItem(
                gameId = 1,
                title = "The Legend Of Zelda Breath of The Wild",
                boxArtUrl = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg",
                quantity = 1,
                unitPrice = 350.0,
                unitPriceWithDiscount = 250.0
        ))

        val request = ApiService.buildService(Endpoints::class.java)
        val response = request.postCheckout(items).execute()
        Assert.assertTrue(response.isSuccessful)
    }
}