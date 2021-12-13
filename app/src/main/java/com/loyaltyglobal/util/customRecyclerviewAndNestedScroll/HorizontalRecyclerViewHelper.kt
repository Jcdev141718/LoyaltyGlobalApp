package com.dhyeyaias.util.customRecyclerviewAndNestedScroll

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * Created by Abhin.
 */
class HorizontalRecyclerViewHelper : RecyclerView {
    private var mGestureDetector: GestureDetector? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        setLayoutManager(layoutManager)
        mGestureDetector = GestureDetector(context, VerticalScrollDetector())
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        return if (mGestureDetector!!.onTouchEvent(e)) {
            false
        } else super.onInterceptTouchEvent(e)
    }

    inner class VerticalScrollDetector : SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return abs(distanceY) > abs(distanceX)
        }
    }
}
