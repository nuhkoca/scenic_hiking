package com.kpit.scenichiking.util.widget

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.kpit.scenichiking.R
import com.kpit.scenichiking.R.styleable
import com.kpit.scenichiking.util.ext.inflate
import com.kpit.scenichiking.util.ext.use

class FadingSnackbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    private var message: TextView? = null
    private var action: Button? = null
    private var length: String? = null

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val view = inflate(R.layout.fading_snackbar_layout, true)
        message = view.findViewById<TextView?>(R.id.snackbar_text)
        action = view.findViewById<Button?>(R.id.snackbar_action)
        id = this.id
        obtainAttributes(attrs)
    }

    private fun dismiss() {
        if (visibility == View.VISIBLE && alpha == 1f) {
            animate().alpha(0f).withEndAction { visibility = View.GONE }.duration = EXIT_DURATION
        }
    }

    fun show(messageText: String?) {
        if (messageText == null) return
        message?.text = messageText
        alpha = 0f
        visibility = View.VISIBLE
        animate().alpha(1f).duration = ENTER_DURATION
        val showDuration =
            ENTER_DURATION + if (length == "long_length") LONG_DURATION else SHORT_DURATION
        Handler().postDelayed({ dismiss() }, showDuration)
    }

    private fun obtainAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, styleable.FadingSnackbar, 0, 0).use {
            length = getString(styleable.FadingSnackbar_length)
        }
    }

    companion object {
        private const val ENTER_DURATION = 300L
        private const val EXIT_DURATION = 200L
        private const val SHORT_DURATION = 1500L
        private const val LONG_DURATION = 2750L
    }
}
