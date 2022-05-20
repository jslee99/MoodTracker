package com.mobileprograming.moodtracker.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.databinding.CalendarCellBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewAdapter(val DiaryList : ArrayList<Diary>) : RecyclerView.Adapter<CalendarViewAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClick(cellData : Diary)
    }

    var itemClickListener : onItemClickListener? = null

    inner class ViewHolder(val binding : CalendarCellBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(DiaryList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.root.layoutParams.height = (parent.height * 0.1666666).toInt()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //날짜 설정, 선택된 Month에 day가 없으면 date ==  -1
        if(DiaryList[position].date != (-1).toLong()) {
            val sdf = SimpleDateFormat("dd")
            val date = Date(DiaryList[position].date)
            val dayString = sdf.format(date)
            holder.binding.calendarCellDayText.text = dayString
            if(DiaryList[position].mood > -1){
                when(DiaryList[position].mood){
                    0 -> holder.binding.calendarCellMoodImage.setImageResource(R.drawable.sohappy_0)
                    1 -> holder.binding.calendarCellMoodImage.setImageResource(R.drawable.happy_1)
                    2 -> holder.binding.calendarCellMoodImage.setImageResource(R.drawable.ok_2)
                    3 -> holder.binding.calendarCellMoodImage.setImageResource(R.drawable.angry_3)
                    4 -> holder.binding.calendarCellMoodImage.setImageResource(R.drawable.sad_4)
                }
            }
        }else{
            holder.binding.calendarCellDayText.text = ""
        }
            //mood image 설정


    }

    override fun getItemCount(): Int {
        return DiaryList.size
    }
}