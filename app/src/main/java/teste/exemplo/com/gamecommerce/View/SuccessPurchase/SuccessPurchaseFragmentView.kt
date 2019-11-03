package teste.exemplo.com.gamecommerce.View.SuccessPurchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_successful_purchase.*
import teste.exemplo.com.gamecommerce.Presenter.SuccessPurchase.ISuccessPurchasePresenter
import teste.exemplo.com.gamecommerce.Presenter.SuccessPurchase.SuccessPurchasePresenter
import teste.exemplo.com.gamecommerce.R
import teste.exemplo.com.gamecommerce.View.Main.MainActivity

class SuccessPurchaseFragmentView : Fragment(), ISuccessPurchaseFragmentView {

    private lateinit var successPurchasePresenter: ISuccessPurchasePresenter

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_successful_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        successPurchasePresenter = SuccessPurchasePresenter(this)
        successPurchasePresenter.eraseCart()

    }

    override fun updateViews(){
        // purchase ID should come from backend response
        purchase_successful_message.text = getString(R.string.purchase_was_successful,279)

        keep_buying.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        (activity as MainActivity).configureToolbar(getString(R.string.purchase_finished), true)
    }

}