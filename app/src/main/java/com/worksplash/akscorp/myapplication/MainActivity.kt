package com.worksplash.akscorp.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_controller_special_scroll.scaleCoefficient = 800f
        view_controller_special_scroll.setScaleView(findViewById(R.id.view_controller_special_background_image))
        view_controller_special_scroll.setHeaderView(findViewById(R.id.view_controller_special_app_bar))
    }
}
