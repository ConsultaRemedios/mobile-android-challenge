package mazer.arthur.gamingshop.data

class GamesRepository(private val apiHelper: ApiHelper){

    suspend fun getBannersList() = apiHelper.getBanners()
    suspend fun getSpotlightList() = apiHelper.getSpotlight()
    suspend fun getGameDetails(id: Int) = apiHelper.getGameDetails(id)
}