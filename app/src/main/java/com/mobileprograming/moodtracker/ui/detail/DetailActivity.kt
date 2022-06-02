package com.mobileprograming.moodtracker.ui.detail

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityDetailBinding
import com.mobileprograming.moodtracker.ui.calendar.CalendarActivity
import com.mobileprograming.moodtracker.util.IntentKey

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init()
    {
        val intent=intent
        val date=intent.getLongExtra(IntentKey.DIARY_KEY,0)

        val db=MyDBHelper(this)
        var diary=db.getDiary(date)
        binding.apply {
            if(diary.size!=0){
                val imageNum=diary[0].mood
                var emotionDraw: Int?=null
                when(imageNum){
                    1 -> emotionDraw = R.drawable.happy_1
                    2 -> emotionDraw=R.drawable.ok_2
                    3 -> emotionDraw=R.drawable.angry_3
                    4 -> emotionDraw=R.drawable.sad_4
                }
                if(emotionDraw!=null){
                    diaryImage.setImageResource(emotionDraw)
                }
                val dateString= diary[0].date.toString()
                diaryDate.text=dateString.substring(0,3)+"년 "+dateString.substring(4,5)+"월 "+dateString.substring(6,7)+"일";

                val imagebyte=diary[0].image
                if (imagebyte != null) {
                    diaryImage.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            imagebyte,
                            0,
                            imagebyte.size
                        )
                    )
                }
            }else{
                diaryDate.text="일기가 존재하지 않습니다."
            }
        }


        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}