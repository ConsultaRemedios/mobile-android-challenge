package teste.exemplo.com.gamecommerce

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.Model.GameAddedToCart
import teste.exemplo.com.gamecommerce.Presenter.Cart.CartAdapterPresenter
import teste.exemplo.com.gamecommerce.Presenter.Cart.CartPresenter
import teste.exemplo.com.gamecommerce.Presenter.Game.GamePresenter
import teste.exemplo.com.gamecommerce.Presenter.Main.MainActivityPresenter
import teste.exemplo.com.gamecommerce.Presenter.SuccessPurchase.SuccessPurchasePresenter
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.View.Cart.CartAdapter
import teste.exemplo.com.gamecommerce.View.Cart.CartFragment
import teste.exemplo.com.gamecommerce.View.Game.GameFragment
import teste.exemplo.com.gamecommerce.View.Main.MainActivity
import teste.exemplo.com.gamecommerce.View.SuccessPurchase.SuccessPurchaseFragmentView

@RunWith(MockitoJUnitRunner::class)
class PresentersUnitTest {

    var gameFragment: GameFragment = Mockito.mock(GameFragment::class.java)
    var cartFragment: CartFragment = Mockito.mock(CartFragment::class.java)
    var cartAdapter: CartAdapter = Mockito.mock(CartAdapter::class.java)
    var successPurchaseFragment: SuccessPurchaseFragmentView = Mockito.mock(SuccessPurchaseFragmentView::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun checkAddGameToCart() {
        // Given
        val gamePresenter = GamePresenter(gameFragment)
        val totalItemsBefore = Cart.totalItems
        // When
        gamePresenter.addGameToCart()
        val totalItemsAfter = Cart.totalItems
        // Then
        assert(totalItemsBefore + 1 == totalItemsAfter)
    }

    @Test
    fun checkCartPresenterGetData() {
        // Given
        val cartPresenter = CartPresenter(cartFragment)
        // When
        cartPresenter.getData()
        // Then
        verify(cartFragment, times(1)).setOnClickListeners()
    }

    @Test
    fun checkAddCartQuantityMethod() {
        // Given
        Cart.items.add(GameAddedToCart(Game(),1))
        val cartAdapterPresenter = CartAdapterPresenter(cartAdapter)
        // When
        cartAdapterPresenter.addCartQuantity(0)
        // Then
        assert(Cart.items[0].qty == 2)
        verify(cartAdapter, times(1)).notifyDataHasChanged()
    }

    @Test
    fun checkRemoveCartQuantityMethod() {
        // Given
        Cart.items.add(GameAddedToCart(Game(),2))
        val cartAdapterPresenter = CartAdapterPresenter(cartAdapter)
        // When
        cartAdapterPresenter.removeCartQuantity(0)
        // Then
        assert(Cart.items[0].qty == 1)
        verify(cartAdapter, times(1)).notifyDataHasChanged()
    }

    @Test
    fun checkRemoveCartQuantityWhenQuantityIsOne() {
        // Given
        Cart.items.add(GameAddedToCart(Game(),1))
        val cartAdapterPresenter = CartAdapterPresenter(cartAdapter)
        // When
        cartAdapterPresenter.removeCartQuantity(0)
        // Then
        assert(Cart.items.isEmpty())
        verify(cartAdapter, times(1)).notifyDataHasChanged()
    }

    @Test
    fun checkEraseCart() {
        // Given
        Cart.items.add(GameAddedToCart(Game(),1))
        val successPurchasePresenter = SuccessPurchasePresenter(successPurchaseFragment)
        // When
        successPurchasePresenter.eraseCart()
        // Then
        assert(Cart.items.isEmpty())
        assert(Cart.totalTax == 0.0)
        assert(Cart.totalPrice == 0.0)
        assert(Cart.totalItems == 0)
        verify(successPurchaseFragment, times(1)).updateViews()
    }
}
