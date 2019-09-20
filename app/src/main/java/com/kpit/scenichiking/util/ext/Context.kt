package com.kpit.scenichiking.util.ext

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Point
import android.view.WindowManager

inline val Context.screenHeight: Int
    get() {
        val point = Point()
        (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(point)
        return point.y
    }
