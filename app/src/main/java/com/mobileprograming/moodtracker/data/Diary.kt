package com.mobileprograming.moodtracker.data

import android.graphics.Bitmap
import java.io.Serializable

data class Diary(
    val date: Long,
    val mood: Int,
    val content: String?
//    val image: Bitmap?
) : Serializable