package com.challange.crandroid.widget

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.widget.NumberPicker
import android.os.Bundle

class NumberPickerDialog(initValue: Int) : DialogFragment() {

    private var valueChangeListener: NumberPicker.OnValueChangeListener? = null
    private val mInitValue = initValue

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val numberPicker = NumberPicker(activity)

        numberPicker.minValue = 0
        numberPicker.maxValue = 6
        numberPicker.wrapSelectorWheel = false
        numberPicker.value = mInitValue

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Quantidade")

        builder.setPositiveButton("OK") { _, _ ->
            valueChangeListener?.onValueChange(
                numberPicker,
                numberPicker.value, numberPicker.value
            )
        }

        builder.setView(numberPicker)
        return builder.create()
    }

    fun setValueChangeListener(valueChangeListener: NumberPicker.OnValueChangeListener) {
        this.valueChangeListener = valueChangeListener
    }
}