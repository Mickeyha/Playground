package com.example.playground.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.example.playground.R

enum class ViewHelper {
    INSTANCE;

    fun changeActionBarColor(actionBar: ActionBar?, context: Context, @ColorRes colorId: Int) {
        val colorDrawable = ColorDrawable()
        colorDrawable.color = ContextCompat.getColor(context, colorId)
        actionBar?.setBackgroundDrawable(colorDrawable)
    }

    fun enableHomeAsUp(actionBar: ActionBar?, isEnable: Boolean) {
        if(isEnable) {
            actionBar?.show()
            actionBar?.setHomeButtonEnabled(true)
            actionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            actionBar?.hide()
        }
    }

    //change the color of homeAsUpIndicator
    fun changeHomeAsUpColor(actionBar: ActionBar?, context: Context, @ColorRes colorId: Int) {
        val drawable = getColorFilterDrawable(context, R.drawable.ic_baseline_arrow_back_24px,colorId)
        actionBar?.setHomeAsUpIndicator(drawable)
    }

    fun getColorFilterDrawable(context: Context, drawableInt: Int, colorInt: Int): Drawable {
        val drawable: Drawable = ContextCompat.getDrawable(context, drawableInt)!!
        @ColorInt val color: Int = ContextCompat.getColor(context, colorInt)
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        return drawable
    }
}