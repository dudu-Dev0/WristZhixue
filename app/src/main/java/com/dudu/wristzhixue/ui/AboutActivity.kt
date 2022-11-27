package com.dudu.wristzhixue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.util.ActivityCollector

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        findViewById<LinearLayout>(R.id.title).setOnClickListener {
            ActivityCollector.addActivity(this)
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

    }
}