package com.example.playground.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.playground.R
import com.example.playground.utils.ext.dpToPx
import kotlinx.android.synthetic.main.dialog_common_confirm.*

class CommonConfirmDialog(
    context: Context,
    @DrawableRes
    private val logoId: Int = -1,
    private val title: String? = null,
    private val content: String,
    private val cancelTitle: String,
    private val confirmTitle: String,
    private val confirmListener: View.OnClickListener? = null) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_common_confirm)

        // logo
        if (logoId > -1) {
            error_image.apply {
                visibility = View.VISIBLE
                setImageDrawable(ContextCompat.getDrawable(context, logoId))
            }
        } else {
            error_image.visibility = View.GONE
        }

        // title
        if (TextUtils.isEmpty(title)) {
            title_text.visibility = View.GONE
        } else {
            title_text.text = title
            title_text.visibility = View.VISIBLE
        }

        // content
        content_text.text = content

        // cancel button
        cancel_button.text = cancelTitle
        cancel_button.setOnClickListener { this.dismiss() }

        // confirm button
        confirm_button.text = confirmTitle
        confirm_button.setOnClickListener {
            confirmListener?.onClick(confirm_button)
        }

        val params = window.attributes
        params.width = 312.dpToPx()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        window.attributes = params
    }
}