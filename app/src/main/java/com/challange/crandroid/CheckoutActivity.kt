package com.challange.crandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        buttonFinalizarCompra.setOnClickListener(clickFinalizarCompra)
        continuarComprando.setOnClickListener(clickContinuarComprando)
    }

    private val clickFinalizarCompra = View.OnClickListener {
        val intent = Intent(this, ConfirmationActivity::class.java)
        startActivity(intent)
    }

    private val clickContinuarComprando = View.OnClickListener {
        val intent = Intent(this, GamesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
