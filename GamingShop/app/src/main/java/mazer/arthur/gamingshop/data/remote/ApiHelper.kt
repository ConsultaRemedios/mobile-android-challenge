package mazer.arthur.gamingshop.data.remote

import mazer.arthur.gamingshop.data.remote.ApiNetwork

class ApiHelper(private val api: ApiNetwork) {

    suspend fun getBanners() = api.fetchBanners()
    suspend fun getSpotlight() = api.fetchSpotlight()
    suspend fun getGameDetails(id: Int) = api.fetchGameDetails(id)
}