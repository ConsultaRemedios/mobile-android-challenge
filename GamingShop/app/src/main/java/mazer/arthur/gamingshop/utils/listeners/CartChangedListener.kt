package mazer.arthur.gamingshop.utils.listeners

interface CartChangedListener {
    fun itemAdded()
    fun itemRemoved()
    fun error()
}