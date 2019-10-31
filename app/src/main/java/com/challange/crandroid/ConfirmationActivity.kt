package com.challange.crandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        continuarComprando.setOnClickListener(clickContinuarComprando)
    }

    override fun onBackPressed() {
        navigateHome()
    }

    private val clickContinuarComprando = View.OnClickListener {
        navigateHome()
    }

    private fun navigateHome() {
        val intent = Intent(this, GamesActivity::class.java)
        navigateUpTo(intent)
    }
}
