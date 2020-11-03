package mazer.arthur.gamingshop.data.remote

class ApiHelper(private val api: ApiNetwork) {

    suspend fun getBanners() = api.fetchBanners()
    suspend fun getSpotlight() = api.fetchSpotlight()
    suspend fun getGameDetails(id: Int) = api.fetchGameDetails(id)
    suspend fun checkout() = api.checkout()
    suspend fun search(term: String) = api.search(term)
}