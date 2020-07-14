package com.mingzheng.lwzzr.view.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.webkit.WebView
import android.widget.EdgeEffect
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import androidx.core.widget.EdgeEffectCompat


class NestedWebView : WebView, NestedScrollingChild3 {

    var mChildHelper: NestedScrollingChildHelper? = null
    var mLastY: Float? = 0f
    var mScrollOffset: IntArray? = intArrayOf(0,0)//滑动偏移
    var mScrollConsumed: IntArray? = intArrayOf(0,0)//滑动消费
    var mNestedOffsetY: Float? = 0f//嵌套偏移
    private var mNestedYOffset: Int = 0

    private var mLastScrollerY: Int = 0

    private var mLastMotionY: Int = 0
    private var mActivePointerId:Int=-1

    private var mEdgeGlowTop: EdgeEffect? = null

    private var mEdgeGlowBottom: EdgeEffect? = null

    private var mVelocityTracker: VelocityTracker? = null

    private var mMaximumVelocity: Int = 0

    private var mMinimumVelocity:Int=0

    private var mIsBeingDragged = false

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val configuration = ViewConfiguration.get(getContext())
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity
        mChildHelper = NestedScrollingChildHelper(this)
        this.setNestedScrollingEnabled(true)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mChildHelper!!.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll(type: Int) {

            mChildHelper!!.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mChildHelper!!.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        mChildHelper!!.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            offsetInWindow, type, consumed
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper!!.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            offsetInWindow, type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper!!.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }



    override fun setNestedScrollingEnabled(b: Boolean) {
//        super.setNestedScrollingEnabled(b)
        mChildHelper!!.isNestedScrollingEnabled = b
        Log.i("xkff","=====setNestedScrollingEnabled=="+b)
    }

    override fun isNestedScrollingEnabled(): Boolean {

        return mChildHelper!!.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {

        return this.startNestedScroll(axes, ViewCompat.TYPE_TOUCH)
    }

    override fun stopNestedScroll() {
        this.stopNestedScroll(ViewCompat.TYPE_TOUCH)
    }

    override fun hasNestedScrollingParent(): Boolean {
        return this.hasNestedScrollingParent(ViewCompat.TYPE_TOUCH)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper!!.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return this.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, ViewCompat.TYPE_TOUCH)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper!!.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper!!.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        initVelocityTrackerIfNotExists()
        var action: Int = ev!!.action
        val vtev = MotionEvent.obtain(ev)
        vtev.offsetLocation(0f, mNestedYOffset.toFloat())
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                this.mNestedYOffset = 0
                this.mLastMotionY = ev.y.toInt()
                mActivePointerId = ev.getPointerId(0)
                this.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
            }

            MotionEvent.ACTION_MOVE -> {
                val activePointerIndex = ev.findPointerIndex(mActivePointerId)
                if (activePointerIndex == -1) {
                    Log.e("xkff", "Invalid pointerId=$mActivePointerId in onTouchEvent")
                }else
                {
                    val y = ev.getY(activePointerIndex).toInt()
                    var deltaY = mLastMotionY - y

                    if (dispatchNestedPreScroll(
                            0, deltaY, mScrollConsumed, mScrollOffset,
                            ViewCompat.TYPE_TOUCH
                        )
                    ) {
                        deltaY -= mScrollConsumed!![1]
                        mNestedYOffset += mScrollOffset!![1]


                    }

                    mLastMotionY = y - mScrollOffset!![1]

                    val oldY = scrollY
                    val range = getScrollRange()
                    val overscrollMode = overScrollMode
                    val canOverscroll =
                        overscrollMode == View.OVER_SCROLL_ALWAYS || overscrollMode == View.OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0

                    val scrolledDeltaY = scrollY - oldY
                    val unconsumedY = deltaY - scrolledDeltaY

                    mScrollConsumed!![1] = 0

                    dispatchNestedScroll(
                        0, scrolledDeltaY, 0, unconsumedY, mScrollOffset,
                        ViewCompat.TYPE_TOUCH, mScrollConsumed!!
                    )

                    mLastMotionY -= mScrollOffset!![1]
                    mNestedYOffset += mScrollOffset!![1]
                    if (canOverscroll) {
                        deltaY -= mScrollConsumed!![1]
                        ensureGlows()
                        val pulledToY = oldY + deltaY
                        if (pulledToY < 0) {
                            EdgeEffectCompat.onPull(
                                mEdgeGlowTop!!, deltaY.toFloat() / height,
                                ev.getX(activePointerIndex) / width
                            )
                            if (!mEdgeGlowBottom!!.isFinished()) {
                                mEdgeGlowBottom!!.onRelease()
                            }
                        } else if (pulledToY > range) {
                            EdgeEffectCompat.onPull(
                                mEdgeGlowBottom!!, deltaY.toFloat() / height,
                                1f - ev.getX(activePointerIndex) / width
                            )
                            if (!mEdgeGlowTop!!.isFinished()) {
                                mEdgeGlowTop!!.onRelease()
                            }
                        }
                        if (mEdgeGlowTop != null && (!mEdgeGlowTop!!.isFinished() || !mEdgeGlowBottom!!.isFinished())) {
                            ViewCompat.postInvalidateOnAnimation(this)
                        }
                    }
                }

            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val velocityTracker = mVelocityTracker
                velocityTracker!!.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                val initialVelocity = velocityTracker!!.getYVelocity(mActivePointerId).toInt()
                if (Math.abs(initialVelocity) >= mMinimumVelocity) {
                    if (!dispatchNestedPreFling(0f, (-initialVelocity).toFloat())) {
                        dispatchNestedFling(0f, (-initialVelocity).toFloat(), true)
                        fling(-initialVelocity)
                    }
                }
//                else if (mScroller.springBack(
//                        scrollX, scrollY, 0, 0, 0,
//                        getScrollRange()
//                    )
//                ) {
//                    ViewCompat.postInvalidateOnAnimation(this)
//                }
                mActivePointerId = -1
                endDrag()
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun ensureGlows() {
        if (overScrollMode != View.OVER_SCROLL_NEVER) {
            if (mEdgeGlowTop == null) {
                val context = context
                mEdgeGlowTop = EdgeEffect(context)
                mEdgeGlowBottom = EdgeEffect(context)
            }
        } else {
            mEdgeGlowTop = null
            mEdgeGlowBottom = null
        }
    }

    internal fun getScrollRange(): Int {
        var scrollRange = 0
        if (childCount > 0) {
            val child = getChildAt(0)
            val lp = child.layoutParams as FrameLayout.LayoutParams
            val childSize = child.height + lp.topMargin + lp.bottomMargin
            val parentSpace = height - paddingTop - paddingBottom
            scrollRange = Math.max(0, childSize - parentSpace)
        }
        return scrollRange
    }

    private fun initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        } else {
            mVelocityTracker!!.clear()
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    fun fling(velocityY: Int) {
        if (childCount > 0) {

//            mScroller.fling(
//                scrollX, scrollY, // start
//                0, velocityY, // velocities
//                0, 0, // x
//                Integer.MIN_VALUE, Integer.MAX_VALUE, // y
//                0, 0
//            ) // overscroll
            runAnimatedScroll(true)
        }
    }

    private fun endDrag() {
        mIsBeingDragged = false

        recycleVelocityTracker()
        stopNestedScroll(ViewCompat.TYPE_TOUCH)

        if (mEdgeGlowTop != null) {
            mEdgeGlowTop!!.onRelease()
            mEdgeGlowBottom!!.onRelease()
        }
    }
    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    private fun runAnimatedScroll(participateInNestedScrolling: Boolean) {
        if (participateInNestedScrolling) {
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_NON_TOUCH)
        } else {
            stopNestedScroll(ViewCompat.TYPE_NON_TOUCH)
        }
        mLastScrollerY = scrollY
        ViewCompat.postInvalidateOnAnimation(this)
    }
}

