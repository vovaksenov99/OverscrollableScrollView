package com.worksplash.akscorp.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.worksplash.akscorp.myapplication.examples_activity.WithHeaderNoScale
import com.worksplash.akscorp.myapplication.examples_activity.WithHeaderScale
import com.worksplash.akscorp.myapplication.examples_activity.WithoutHeader
import kotlinx.android.synthetic.main.menu_activity.*


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        with_header_scale.setOnClickListener { startActivity(Intent(this, WithHeaderScale::class.java)) }
        with_header_no_scale.setOnClickListener { startActivity(Intent(this, WithHeaderNoScale::class.java)) }
        without_header.setOnClickListener { startActivity(Intent(this, WithoutHeader::class.java)) }
    }


}