package com.challange.crandroid.singleton

import com.challange.crandroid.data.CartItem
import java.io.Serializable

object Cart : Serializable {

    var itens: MutableList<CartItem> = arrayListOf()
    var valorFrete: Double = 0.0
    var valorTotal: Double = 0.0

    fun calcularCarrinho() {
        itens.forEach {
            it.precoSomaQuantidade = it.game.price * it.quantidade
        }

        valorFrete = itens.sumByDouble { (10.00 * it.quantidade) }
        valorTotal = itens.sumByDouble { it.precoSomaQuantidade } + valorFrete
        if (valorTotal > 250.00)
            valorFrete = 0.00
    }

    fun limparCarrinho() {
        itens.removeAll { true }
        calcularCarrinho()
    }
}