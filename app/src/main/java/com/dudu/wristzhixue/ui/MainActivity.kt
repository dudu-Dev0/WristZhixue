package com.dudu.wristzhixue.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.util.ActivityCollector
import com.dudu.wristzhixue.util.NetworkUtil
import com.dudu.wristzhixue.util.Urls.Companion.student
import com.dudu.wristzhixue.widget.MarqueeTextView
import org.json.JSONObject
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private lateinit var usr : String
    private lateinit var pwd : String
    lateinit var nameText:TextView
    lateinit var classText:TextView
    lateinit var schoolText: MarqueeTextView
    lateinit var swipeFresh:SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameText = findViewById(R.id.tv_username)
        classText = findViewById(R.id.tv_class)
        schoolText = findViewById(R.id.tv_school)
        swipeFresh = findViewById(R.id.main_swipe_fresh)
        findViewById<LinearLayout>(R.id.title).setOnClickListener {
            ActivityCollector.addActivity(this)
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

        usr = getSharedPreferences("login-information", MODE_PRIVATE).getString("usr", "")!!
        pwd = getSharedPreferences("login-information", MODE_PRIVATE).getString("pwd", "")!!

        swipeFresh.setColorSchemeColors(resources.getColor(R.color.zhixue_green))
        swipeFresh.setDistanceToTriggerSync(60)
        swipeFresh.setProgressViewOffset(false, 30, 160)

        swipeFresh.isRefreshing = true

        checkLoginStatus()

        swipeFresh.setOnRefreshListener {
            initData()
        }


    }

    fun checkLoginStatus() {   //true:已登录 false:未登录
        if (usr == "" || pwd == "") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            thread {
                val response = NetworkUtil.get("$student?usr=$usr&pwd=$pwd").body.string()
                val jsonObject = JSONObject(response)
                val statusJSON: JSONObject = jsonObject.optJSONObject("status")!!
                val status = statusJSON.optInt("code")
                runOnUiThread {
                    if (status == -1) {
                        Toast.makeText(this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    if (status == 0) {
                        Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show()
                        initData()
                        swipeFresh.isRefreshing = false
                    }
                }
            }
        }

    }
    fun initData(){
        thread {
            val response = NetworkUtil.get("$student?usr=$usr&pwd=$pwd").body.string()
            val jsonObject = JSONObject(response)
            runOnUiThread {
                val resultJson = jsonObject.optJSONObject("result")!!
                nameText.text = resultJson.optString("name")
                val classJson = resultJson.optJSONObject("class")!!
                classText.text = classJson.optString("name")
                val schoolJson = classJson.optJSONObject("school")!!
                schoolText.text = schoolJson.optString("name")
                swipeFresh.isRefreshing = false
            }

        }
    }
}