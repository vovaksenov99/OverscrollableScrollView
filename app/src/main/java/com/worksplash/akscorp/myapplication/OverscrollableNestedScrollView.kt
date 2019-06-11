package com.worksplash.akscorp.myapplication

import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

class OverscrollableNestedScrollView : NestedScrollView {

    var maxOverscrollDistance: Int = 200
        set(value) {
            field = (getScreenDensity() * value).toInt()
        }
    var scaleCoefficient: Float = 500f
        set(value) {
            field = value / getScreenDensity()
        }
    var pullUpAnimationTime: Long = 500

    var pullUpInterpolator: Interpolator = AccelerateDecelerateInterpolator()

    private var scaleView: View? = null
    private var headerView: View? = null

    private var startHeaderSize = 0
    private var startMoveY: Float = 0f
    private var currentOverscrollY = 0
    private var lastYDelta = 0

    private var lastScrollY = 0
    private var lastHeaderYPos = 0

    private var pullUpAnimator: ValueAnimator = ValueAnimator()

    init {
        overScrollMode = OVER_SCROLL_ALWAYS
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun overScrollBy(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean = super.overScrollBy(
        deltaX,
        deltaY,
        scrollX,
        scrollY,
        scrollRangeX,
        0,
        maxOverScrollX,
        maxOverscrollDistance,
        isTouchEvent
    )

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)

        if (scrollY > 0) {
            currentOverscrollY = 0
        } else {
            super.scrollTo(scrollX, 0)
            currentOverscrollY = scrollY
            updateHeaderViewState()
            updateScaleViewState()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean =
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                initScrollAction(ev)
                super.dispatchTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                val currentY = ev.rawY
                lastYDelta = (startMoveY - currentY).toInt()

                if ((lastScrollY > 0 || lastHeaderYPos < 0) && lastYDelta < 0 && y.toInt() == startHeaderSize) {
                    initScrollAction(ev)
                    true
                } else if (lastYDelta < 0 && y == headerView?.measuredHeight?.toFloat()) {
                    pullDown(lastYDelta)
                    false
                } else {
                    super.dispatchTouchEvent(ev)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (currentOverscrollY < 0) {
                    pullUp()
                    true
                } else {
                    super.dispatchTouchEvent(ev)
                }
            }
            else -> {
                super.dispatchTouchEvent(ev)
            }
        }

    fun setScaleView(v: View?) {
        scaleView = v
    }

    fun setHeaderView(headerView: View) {
        headerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        startHeaderSize = headerView.measuredHeight

        this.headerView = headerView
    }

    fun getHeaderView() = headerView

    fun getScaleView() = scaleView

    private fun updateHeaderViewState() {
        headerView?.let {
            it.layoutParams?.height = startHeaderSize - currentOverscrollY
            it.requestLayout()
            lastHeaderYPos = it.y.toInt()
        }
    }

    private fun updateScaleViewState() {
        scaleView?.let {
            it.scaleX = -currentOverscrollY / scaleCoefficient + 1.0f
            it.scaleY = -currentOverscrollY / scaleCoefficient + 1.0f
        }
    }

    private fun initScrollAction(ev: MotionEvent) {
        startMoveY = ev.rawY
        lastScrollY = this.scrollY
        lastHeaderYPos = headerView?.y?.toInt() ?: 0
    }

    private fun pullUp() {
        pullUpAnimator = ValueAnimator.ofInt(currentOverscrollY, 0)
        pullUpAnimator.apply {
            interpolator = pullUpInterpolator
            duration = pullUpAnimationTime
            addUpdateListener {
                val delta = it.animatedValue as Int
                overScrollBy(0, delta, 0, 0, 0, 0, 0, maxOverscrollDistance, true)
            }
        }
        pullUpAnimator.start()

    }

    private fun pullDown(delta: Int) {
        overScrollBy(0, delta, 0, 0, 0, 0, 0, maxOverscrollDistance, true)
    }

    private fun getScreenDensity() = context.resources.displayMetrics.density

}
