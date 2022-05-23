package com.mobileprograming.moodtracker.ui.diarylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityDiaryListBinding

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
            //TODO
        }
        val database=MyDBHelper(this)
        var diaryList= database.getAllDiary()
        diaryListBinding.recyclerView.adapter=MyAdapter(diaryList)
    }
}