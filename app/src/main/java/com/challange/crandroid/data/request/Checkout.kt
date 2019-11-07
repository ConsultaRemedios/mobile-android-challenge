package com.challange.crandroid.data.request


import com.google.gson.annotations.SerializedName

data class Checkout(

    val games: List<Int>,

    @SerializedName("endereco_entrega")
    val enderecoEntrega: EnderecoEntrega,

    @SerializedName("valor_frete")
    val valorFrete: Double
)