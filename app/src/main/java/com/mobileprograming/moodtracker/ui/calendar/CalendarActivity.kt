package com.mobileprograming.moodtracker.ui.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityCalendarBinding
import com.mobileprograming.moodtracker.ui.detail.DetailActivity
import com.mobileprograming.moodtracker.ui.writing.WritingActivity
import com.mobileprograming.moodtracker.util.IntentKey
import java.lang.System.currentTimeMillis
import java.time.LocalDate
import java.util.*
import kotlin.system.measureTimeMillis

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var diaryList : List<Diary>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDB()
        initList()
    }

    private fun initDB() {
        myDBHelper = MyDBHelper(this)
    }

    private fun initList() {
        // 아래 함수를 통해 데이터베이스에서 다이어리 리스트 호출 가능
        // diaryList = myDBHelper.getAllDiary()
        // 테스트용 다이어리 리스트입니다.
        diaryList = listOf(
            Diary(0,0,"test 0",
                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
            Diary(1,1,"test 1",
                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
            Diary(2,2,"test 2",
                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()))
    }

    // 무드를 입력받아 WritingActivity 이동
    private fun intentWriteDiary(mood:Int){
        val intent = Intent(this, WritingActivity::class.java)
        intent.putExtra(IntentKey.MOOD_KEY, mood)
        startActivity(intent)
    }

    // Diary 객체를 받아서 DetailActivity 이동
    private fun intentDetail(diary: Diary){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(IntentKey.DIARY_KEY, diary)
        startActivity(intent)
    }
}