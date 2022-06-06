package com.mobileprograming.moodtracker.ui.diarylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityDiaryListBinding
import com.mobileprograming.moodtracker.ui.writing.WritingActivity

class DiaryListActivity : AppCompatActivity() {
    lateinit var diaryListBinding: ActivityDiaryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diaryListBinding= ActivityDiaryListBinding.inflate(layoutInflater)
        setContentView(diaryListBinding.root)
        init()
    }
    private fun init()
    {
        diaryListBinding.addButton.setOnClickListener {
            val intent= Intent(this, WritingActivity::class.java)
            startActivity(intent)
        }
        val database=MyDBHelper(this)
        var diaryList= database.getAllDiary()
        val exceptNullList=ArrayList<Diary>() // content내용이 비어있고 기분만 저장되어있는 경우 -> 애초에 list에서 제외 시켜 보여주지 않음
        val i:Int=0
        for(diary in diaryList){
            if(diary.content!=null){
                exceptNullList.add(diary)
            }
        }
        diaryListBinding.recyclerView.adapter=MyAdapter(exceptNullList.reversed()) // 나중에 일기를 쓰고 나서 데이더가 업데이트 되었을때 오늘분의 일기가 표시될지 고려해봐야됨
        diaryListBinding.closeButton.setOnClickListener {
            onBackPressed()
        }
    }
}