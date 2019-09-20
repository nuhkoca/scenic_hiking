package com.kpit.scenichiking.util.provider

import android.content.Context
import com.kpit.scenichiking.R
import javax.inject.Inject

class HikingDimenProvider @Inject constructor(private val context: Context) : DimenProvider {

    override fun getMapPaddingTopInPx() =
        context.resources.getDimensionPixelSize(R.dimen.map_top_padding)

    override fun getMapPaddingRightInPx() =
        context.resources.getDimensionPixelSize(R.dimen.map_right_padding)

    override fun getMapPaddingLeftInPx() =
        context.resources.getDimensionPixelSize(R.dimen.map_left_padding)

    override fun getMapPaddingBottomInPx() =
        context.resources.getDimensionPixelSize(R.dimen.map_bottom_padding)
}
