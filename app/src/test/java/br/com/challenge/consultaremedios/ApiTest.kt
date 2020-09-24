package br.com.challenge.consultaremedios

import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.MobileTestService
import org.junit.Assert
import org.junit.Test

class ApiTest {

    @Test
    fun getBannerTest() {
        val request = MobileTestService.buildService(Endpoints::class.java)
        val response = request.getBanners().execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun getSpotlightTest() {
        val request = MobileTestService.buildService(Endpoints::class.java)
        val response = request.getSpotlight().execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun getGameDetailsTest() {
        val request = MobileTestService.buildService(Endpoints::class.java)
        val response = request.getGameDetails(1).execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun searchGamesTest() {
        val request = MobileTestService.buildService(Endpoints::class.java)
        val response = request.searchGames("zelda").execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun postCheckoutTest() {
        val request = MobileTestService.buildService(Endpoints::class.java)
        val response = request.postCheckout(mapOf("game_id" to 1)).execute()
        Assert.assertTrue(response.isSuccessful)
    }
}