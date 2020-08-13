package mazer.arthur.gamingshop.utils.listeners

interface ShippingChangedListener {
    fun onShippingValueChanged(value: Int)
    fun emptyCart()
}