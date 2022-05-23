package com.mobileprograming.moodtracker.ui.writing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityWritingBinding
import com.mobileprograming.moodtracker.util.IntentKey

class WritingActivity : AppCompatActivity() {

    lateinit var binding:ActivityWritingBinding
    lateinit var myDBHelper:MyDBHelper

    // 테스트용 초기 mood 값
    private var mood = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDB()

        // 아래 함수를 통해 무드를 전달받습니다.
        // var mood = intent.getIntExtra(IntentKey.MOOD_KEY,0)
    }


    private fun initDB() {
        myDBHelper = MyDBHelper(this)
    }



    // 데이터베이스에 diary 추가 예제 코드일 뿐입니다.
    // 삽입 방식이 이해되셨다면 지우셔도 됩니다.
//    private fun insert(){
//         이미지 비트맵 가져오기 ( 예시, R.drawable.happy_1 )
//        val bitmap = ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()
//
//         다이어리 객체 생성
//        val diary = Diary(System.currentTimeMillis(), 0, "test content", bitmap)
//
//         db 삽입
//        myDBHelper.insert(diary)
//    }
}