package mazer.arthur.gamingshop.data

import mazer.arthur.gamingshop.models.Banner
import mazer.arthur.gamingshop.models.GameDetails
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiNetwork {

    @GET(ApiConstants.BANNER)
    suspend fun fetchBanners(): ArrayList<Banner>

    @GET(ApiConstants.SPOTLIGHT)
    suspend fun fetchSpotlight(): ArrayList<GameDetails>

    @GET(ApiConstants.GAME)
    suspend fun fetchGameDetails(@Path("id") id: Int): GameDetails

}
