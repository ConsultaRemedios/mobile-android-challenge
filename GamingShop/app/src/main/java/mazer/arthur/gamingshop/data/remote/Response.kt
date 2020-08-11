package mazer.arthur.gamingshop.data.remote

import mazer.arthur.gamingshop.domain.models.Status

data class Response<out T>(val status: Status, val data: T?, val message: String?) {
    companion object{
        fun <T> loading(): Response<T> =
            Response(
                Status.LOADING,
                null,
                null
            )
        fun <T> success(data: T): Response<T> =
            Response(
                Status.SUCCESS,
                data,
                null
            )
        fun <T> error(data: T?, message: String): Response<T> =
            Response(
                Status.ERROR,
                data,
                message
            )
    }
}