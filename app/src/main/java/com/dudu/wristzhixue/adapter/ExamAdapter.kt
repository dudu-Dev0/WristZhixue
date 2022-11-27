package com.dudu.wristzhixue.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.bean.Exam
import com.dudu.wristzhixue.ui.MarkActivity
import com.dudu.wristzhixue.util.MyApplication

class ExamAdapter(val examList: List<Exam>):RecyclerView.Adapter<ExamAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val text:TextView = view.findViewById(R.id.item_exam_name_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exam_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = examList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exam = examList[position]
        holder.text.text = exam.name
        holder.itemView.addClickScale()
        holder.itemView.setOnClickListener {
            val intent = Intent(MyApplication.context,MarkActivity::class.java)
            if((Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)){
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtra("exam_name",examList[position].name)
            intent.putExtra("exam_id",examList[position].id)
            MyApplication.context.startActivity(intent)
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