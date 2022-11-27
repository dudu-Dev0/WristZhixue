package com.dudu.wristzhixue.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.util.ActivityCollector

class MenuActivity : AppCompatActivity() {
    lateinit var home:FrameLayout
    lateinit var exams:FrameLayout
    lateinit var settings:FrameLayout
    lateinit var about:FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        home = findViewById(R.id.menu_home)
        exams = findViewById(R.id.menu_exams)
        settings = findViewById(R.id.menu_settings)
        about = findViewById(R.id.menu_about)

        home.addClickScale()
        exams.addClickScale()
        settings.addClickScale()
        about.addClickScale()

        home.setOnClickListener {
            ActivityCollector.finishAll()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        exams.setOnClickListener {
            ActivityCollector.finishAll()
            val intent = Intent(this,ExamsActivity::class.java)
            startActivity(intent)
            finish()
        }
        settings.setOnClickListener {
            ActivityCollector.finishAll()
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
        about.setOnClickListener {
            ActivityCollector.finishAll()
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun View.addClickScale(scale: Float = 0.9f, duration: Long = 150) {
        this.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    this.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    this.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
                }
            }
            // 点击事件处理，交给View自身
            this.onTouchEvent(event)
        }
    }
}