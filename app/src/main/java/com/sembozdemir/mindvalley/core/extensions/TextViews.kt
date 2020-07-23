package com.sembozdemir.mindvalley.core.extensions

import android.widget.TextView
import androidx.core.view.isVisible

fun TextView.setTextIfExists(text: CharSequence) {
    isVisible = text.isNotEmpty()
    this.text = text
}