package com.example.playground.utils.ext

import android.content.res.Resources
import android.util.TypedValue

fun Int.dpToPx():Int {
    val metrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}