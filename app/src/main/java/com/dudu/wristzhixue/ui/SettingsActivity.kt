package com.dudu.wristzhixue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.util.ActivityCollector

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<LinearLayout>(R.id.title).setOnClickListener {
            ActivityCollector.addActivity(this)
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.ll_log_out).setOnClickListener {
            val SPEditor = getSharedPreferences("login-information", MODE_PRIVATE).edit()
            SPEditor.putString("usr","")
            SPEditor.putString("pwd","")
            SPEditor.apply()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}