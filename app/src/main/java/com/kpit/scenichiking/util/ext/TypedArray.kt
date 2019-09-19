package com.kpit.scenichiking.util.ext

import android.content.res.TypedArray

fun TypedArray.use(block: TypedArray.() -> Unit) {
    try {
        block()
    } finally {
        recycle()
    }
}
