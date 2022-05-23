package com.mobileprograming.moodtracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobileprograming.moodtracker.ui.calendar.CalendarActivity
import com.mobileprograming.moodtracker.ui.detail.DetailActivity
import com.mobileprograming.moodtracker.ui.diarylist.DiaryListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, DiaryListActivity::class.java))
    }
}