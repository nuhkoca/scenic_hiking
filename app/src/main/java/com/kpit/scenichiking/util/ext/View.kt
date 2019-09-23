package com.kpit.scenichiking.util.ext

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation.TRANSLATION_Y
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY
import androidx.dynamicanimation.animation.SpringForce.STIFFNESS_LOW

private const val DEFAULT_VELOCITY = 1000f
private const val DEFAULT_FINAL_POSITION = 1.0f

fun View.show() {
    visibility = View.VISIBLE
}

fun View.slideDown() {
    show()
    SpringAnimation(this, TRANSLATION_Y, DEFAULT_FINAL_POSITION).apply {
        spring.stiffness = STIFFNESS_LOW
        spring.dampingRatio = DAMPING_RATIO_HIGH_BOUNCY
        setStartVelocity(DEFAULT_VELOCITY)
        start()
    }
}
