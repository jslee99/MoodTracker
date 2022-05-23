package com.mobileprograming.moodtracker.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.databinding.ActivityDetailBinding
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
        val diary = Diary(0,3, "test content",
        ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap())
        // 아래 코드를 통해 diary 객체를 받아옵니다
        // val diary = intent.getSerializableExtra(IntentKey.DIARY_KEY) as Diary
    }
}