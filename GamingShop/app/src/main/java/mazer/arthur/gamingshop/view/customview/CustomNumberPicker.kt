package mazer.arthur.gamingshop.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.utils.listeners.OnNumberPickerChanged

class CustomNumberPicker(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs){

    private var tvNumber: TextView
    private var ivMinus: ImageView
    private var ivPlus: ImageView

    private var listener: OnNumberPickerChanged? = null

    companion object{
        const val MIN_VALUE = 1
    }

    init {
        View.inflate(context, R.layout.view_numberpicker, this)
        tvNumber = findViewById(R.id.tvNumber)
        ivMinus = findViewById(R.id.ivMinus)
        ivPlus = findViewById(R.id.ivPlus)

        tvNumber.text = MIN_VALUE.toString()

        ivMinus.setOnClickListener(ClickHandler(-1))
        ivPlus.setOnClickListener(ClickHandler(1))
    }


    inner class ClickHandler(val sum: Int): OnClickListener {
        override fun onClick(p0: View?) {
            var result = getNumber() + sum
            if (result < MIN_VALUE){
                result = MIN_VALUE
            }
            listener?.onValueChanged(result)
            tvNumber.text = result.toString()
        }
    }

    fun setOnNumberChangedListener(listener: OnNumberPickerChanged){
        this.listener = listener
    }

    fun setValue(value: Int){
        tvNumber.text = value.toString()
    }

    fun getNumber(): Int{
        val value = tvNumber.text.toString()
        return try{
            value.toInt()
        }catch(ex: Exception){
            1
        }
    }

}