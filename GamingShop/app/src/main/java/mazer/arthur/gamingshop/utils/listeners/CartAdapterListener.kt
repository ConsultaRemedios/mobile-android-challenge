package mazer.arthur.gamingshop.utils.listeners

interface CartAdapterListener {
    fun onDeleteClicked(id: Int)
    fun onNumberPickerChanged(id: Int, value: Int)
}