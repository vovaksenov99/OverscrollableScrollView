package com.worksplash.akscorp.myapplication.examples_activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.worksplash.akscorp.myapplication.R
import kotlinx.android.synthetic.main.with_header.*

class WithHeaderScale : AppCompatActivity() {

    var isExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.with_header)

        initOverscrolScrollView()
        initAppBar()
    }

    private fun initOverscrolScrollView() {
        special_scroll.scaleCoefficient = 800f
        special_scroll.setScaleView(findViewById(R.id.background_image))
        special_scroll.setHeaderView(findViewById(R.id.app_bar))
    }


    private fun initAppBar() {

        val elevationAnimator = PropertyValuesHolder.ofFloat("elevation", 30f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(app_bar, elevationAnimator).apply {
            duration = 500
        }
        special_scroll.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY >= 1) {
                if (isExpanded) {
                    isExpanded = false
                    animator.start()
                }

            } else {
                if (!isExpanded) {
                    isExpanded = true
                    animator.cancel()
                    app_bar.elevation = 0f
                }
            }
        }
    }

}