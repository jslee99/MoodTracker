package com.mobileprograming.moodtracker.ui.diarylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityDiaryBinding

class DiaryListActivity : AppCompatActivity() {

    lateinit var binding:ActivityDiaryBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var diaryList: List<Diary>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
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

        // 테스트용 다이어리 리스트입니다
//        diaryList = listOf(
//            Diary(0,0,"test 0",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
//            Diary(1,1,"test 1",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
//            Diary(2,2,"test 2",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()))
    }
}