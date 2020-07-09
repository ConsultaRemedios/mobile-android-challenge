package br.com.angelorobson.templatemvi.view.utils

import android.widget.ImageButton

fun ImageButton.enable() {
    this.isEnabled = true
    this.imageAlpha = 0xFF
}

fun ImageButton.disable() {
    this.isEnabled = false
    this.imageAlpha = 0x3F
}