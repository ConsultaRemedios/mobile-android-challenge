package com.challange.crandroid.data.request


import com.google.gson.annotations.SerializedName

data class EnderecoEntrega(
    val cep: String,
    val logradouro: String,
    val numero: String,
    val cidade: String,
    val estado: String,
    val pais: String
)