package com.kpit.scenichiking.util.config

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

object OsUtil {

    fun hasMAndAbove() = VERSION.SDK_INT >= VERSION_CODES.M
}
