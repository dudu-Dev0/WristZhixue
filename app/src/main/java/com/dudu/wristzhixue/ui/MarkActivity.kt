package com.dudu.wristzhixue.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.roundcornerprogressbar.indeterminate.IndeterminateCenteredRoundCornerProgressBar
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.adapter.ExamAdapter
import com.dudu.wristzhixue.adapter.MarkAdapter
import com.dudu.wristzhixue.bean.Exam
import com.dudu.wristzhixue.bean.Mark
import com.dudu.wristzhixue.util.NetworkUtil
import com.dudu.wristzhixue.util.Urls
import com.dudu.wristzhixue.widget.MarqueeTextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import kotlin.concurrent.thread

class MarkActivity : AppCompatActivity() {
    private lateinit var title :MarqueeTextView
    private lateinit var usr : String
    private lateinit var pwd : String
    private lateinit var progressBar: IndeterminateCenteredRoundCornerProgressBar
    private lateinit var examRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark)

        usr = getSharedPreferences("login-information", MODE_PRIVATE).getString("usr", "")!!
        pwd = getSharedPreferences("login-information", MODE_PRIVATE).getString("pwd", "")!!

        title = findViewById(R.id.tv_mark_title)
        progressBar = findViewById(R.id.pg_marks)
        examRecyclerView = findViewById(R.id.rv_marks)

        title.text = intent.getStringExtra("exam_name")
        val examId = intent.getStringExtra("exam_id")

        thread {
            val response = NetworkUtil.get("${Urls.mark}?usr=$usr&pwd=$pwd&exam=$examId").body.string()
            runOnUiThread {
                progressBar.visibility = View.GONE
            }
            val markResponse = JSONObject(response).getJSONObject("result").getJSONArray("mark")
            val typeOf = object : TypeToken<List<Mark>>() {}.type
            val gson = Gson()
            val markList = gson.fromJson<List<Mark>>(markResponse.toString(),typeOf)
            runOnUiThread {
                examRecyclerView.layoutManager = LinearLayoutManager(this)
                examRecyclerView.adapter = MarkAdapter(markList)
            }

        }
    }
}