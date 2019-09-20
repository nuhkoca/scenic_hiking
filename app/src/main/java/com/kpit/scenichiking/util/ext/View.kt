package com.kpit.scenichiking.util.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnStart

const val DEFAULT_ANIM_DURATION = 900L
const val DEFAULT_INTERPOLATOR_FACTOR = 1.5f

fun View.show() {
    visibility = View.VISIBLE
}

inline fun View.slideDown(
    duration: Long = DEFAULT_ANIM_DURATION,
    crossinline doOnStart: (animator: Animator) -> Unit = {}
) {
    ValueAnimator.ofFloat(-(context.screenHeight).toFloat(), 0f).apply {
        doOnStart { doOnStart.invoke(it) }
        addUpdateListener { translationY = it.animatedValue as Float }
        interpolator = AccelerateInterpolator(DEFAULT_INTERPOLATOR_FACTOR)
        setDuration(duration)
        start()
    }
}
