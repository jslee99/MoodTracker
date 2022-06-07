package com.mobileprograming.moodtracker.ui.diarylist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityDiaryListBinding
import com.mobileprograming.moodtracker.ui.writing.WritingActivity


class DiaryListActivity : AppCompatActivity() {
    lateinit var diaryListBinding: ActivityDiaryListBinding
    private val database=MyDBHelper(this)
    private var myadapter:MyAdapter?=null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diaryListBinding= ActivityDiaryListBinding.inflate(layoutInflater)
        setContentView(diaryListBinding.root)
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            myadapter?.updateData(database)
        }
        init()
    }
    private fun init()
    {

        var diaryList= database.getAllDiary()
        val exceptNullList=ArrayList<Diary>()
        val i:Int=0
        for(diary in diaryList){
            if(diary.content!="" || diary.image!=null){ //사진, 일기 내용 둘 다 없으면 recycler 뷰에 뜨지 않음
                exceptNullList.add(diary)
            }
        }
        myadapter=MyAdapter(exceptNullList.reversed().toMutableList())
        diaryListBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(this,
            LinearLayoutManager.VERTICAL)
        )
        diaryListBinding.recyclerView.adapter=myadapter
        diaryListBinding.closeButton.setOnClickListener {
            onBackPressed()
        }
        diaryListBinding.addButton.setOnClickListener {
            val intent= Intent(this, WritingActivity::class.java)
            resultLauncher?.launch(intent)
        }
    }
}