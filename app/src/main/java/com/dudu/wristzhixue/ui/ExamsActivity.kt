package com.dudu.wristzhixue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.akexorcist.roundcornerprogressbar.indeterminate.IndeterminateCenteredRoundCornerProgressBar
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.adapter.ExamAdapter
import com.dudu.wristzhixue.bean.Exam
import com.dudu.wristzhixue.util.ActivityCollector
import com.dudu.wristzhixue.util.NetworkUtil
import com.dudu.wristzhixue.util.Urls
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import kotlin.concurrent.thread

class ExamsActivity : AppCompatActivity() {
    private lateinit var usr : String
    private lateinit var pwd : String
    private lateinit var progressBar: IndeterminateCenteredRoundCornerProgressBar
    private lateinit var examRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exams)

        usr = getSharedPreferences("login-information", MODE_PRIVATE).getString("usr", "")!!
        pwd = getSharedPreferences("login-information", MODE_PRIVATE).getString("pwd", "")!!

        progressBar = findViewById(R.id.pg_exams)
        examRecyclerView = findViewById(R.id.rv_exams)

        findViewById<LinearLayout>(R.id.title).setOnClickListener {
            ActivityCollector.addActivity(this)
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }
        thread {
            val response = NetworkUtil.get("${Urls.allExam}?usr=$usr&pwd=$pwd").body.string()
            runOnUiThread {
                progressBar.visibility = View.GONE
            }
            val examResponse = JSONObject(response).getJSONArray("result")
            val typeOf = object : TypeToken<List<Exam>>() {}.type
            val gson = Gson()
            val examList = gson.fromJson<List<Exam>>(examResponse.toString(),typeOf)
            runOnUiThread {
                examRecyclerView.layoutManager = LinearLayoutManager(this)
                examRecyclerView.adapter = ExamAdapter(examList)
            }

        }
    }
}