package com.mobileprograming.moodtracker.ui.diarylist

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.databinding.DiarylistRowBinding
import android.graphics.Bitmap as Bitmap1

class MyAdapter (val diaryList:ArrayList<Diary>)
    :RecyclerView.Adapter<MyAdapter.ViewHolder>(){
        inner class ViewHolder(val binding:DiarylistRowBinding):RecyclerView.ViewHolder(binding.root)
        {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DiarylistRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content=diaryList[position].content
        holder.binding.diaryContent.text = content
        val imageNum = diaryList[position].mood
        var emotionDraw: Int? = null
        when (imageNum) {
            1 -> emotionDraw = R.drawable.happy_1
            2 -> emotionDraw = R.drawable.ok_2
            3 -> emotionDraw = R.drawable.angry_3
            4 -> emotionDraw = R.drawable.sad_4
        }
        holder.binding.emotionImage.setImageResource(emotionDraw!!)
        val dateString = diaryList[position].date.toString()
        holder.binding.diaryDate.text = dateString
        val imagebyte = diaryList[position].image
        if (imagebyte != null) {
            holder.binding.diaryImage.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    imagebyte,
                    0,
                    imagebyte.size
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }
}