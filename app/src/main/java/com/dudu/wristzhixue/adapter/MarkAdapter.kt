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
import com.dudu.wristzhixue.bean.Mark
import com.dudu.wristzhixue.ui.MarkActivity
import com.dudu.wristzhixue.util.MyApplication

class MarkAdapter(val markList: List<Mark>):RecyclerView.Adapter<MarkAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val subjectText:TextView = view.findViewById(R.id.tv_subject_mark_item)
        val rankText:TextView = view.findViewById(R.id.tv_rank_mark_item)
        val scoreText:TextView = view.findViewById(R.id.tv_score_mark_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mark_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = markList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mark = markList[position]

        holder.subjectText.text = mark.subject.name
        holder.rankText.text = "班级排名:${mark.classRank}"
        holder.scoreText.text = "${mark.score}/${mark.subject.standardScore}"

        holder.itemView.addClickScale()
        holder.itemView.setOnClickListener {
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