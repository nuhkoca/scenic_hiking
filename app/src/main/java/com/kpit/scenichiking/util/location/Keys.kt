package com.kpit.scenichiking.util.location

object Keys {

    private const val NATIVE_LIB = "native-lib"

    init {
        System.loadLibrary(NATIVE_LIB)
    }

    external fun mapBoxKey(): String
}
