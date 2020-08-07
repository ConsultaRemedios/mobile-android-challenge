package br.com.weslleymaciel.gamesecommerce

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : Application()  {
    companion object {
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }
}