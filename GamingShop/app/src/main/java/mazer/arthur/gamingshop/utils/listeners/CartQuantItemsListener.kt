package mazer.arthur.gamingshop.utils.listeners

interface CartQuantItemsListener {
    fun hasItems(quant: Int)
    fun emptyCart()
}