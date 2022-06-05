package com.mobileprograming.moodtracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mobileprograming.moodtracker.ui.calendar.CalendarActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = Handler()
        handler.postDelayed(
            {
                startActivity(Intent(this, CalendarActivity::class.java))
                finish()
            },1000
        )

    }
}