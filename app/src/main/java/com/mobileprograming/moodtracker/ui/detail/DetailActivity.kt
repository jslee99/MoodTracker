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

    // 테스트용 Diary 객체
//    private val diary = Diary(0,3, "test content",
//        ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init()
    {
        val intent=intent
        val date=intent.getLongExtra("date",0)//TODO name값 수정 필요

        var diary:Diary?=null
        val db=MyDBHelper(this)
        val diarylist=db.getAllDiary()
        for(getDiary in diarylist){
            if(getDiary.date==date){ //TODO 수정 필요
                diary=getDiary
                break
            }
        }

        val imageNum=diary?.mood
        var emotionDraw: Int?=null
        when(imageNum){
            1 -> emotionDraw = R.drawable.happy_1
            2 -> emotionDraw=R.drawable.ok_2
            3 -> emotionDraw=R.drawable.angry_3
            4 -> emotionDraw=R.drawable.sad_4
        }
        binding.apply {
            if(emotionDraw!=null){
                diaryImage.setImageResource(emotionDraw)
            }
            val dateString= diary?.date.toString()
            diaryDate.text=dateString

            val imagebyte=diary?.image
            if (imagebyte != null) {
                diaryImage.setImageBitmap(BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.size))
            }
        }
        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}