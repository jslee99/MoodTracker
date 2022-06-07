package com.mobileprograming.moodtracker.ui.diarylist

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diaryListBinding= ActivityDiaryListBinding.inflate(layoutInflater)
        setContentView(diaryListBinding.root)
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            update()
        }
        init()
    }
    private fun init()
    {

        var diaryList= database.getAllDiary()
        val exceptNullList=ArrayList<Diary>() // content내용이 비어있고 기분만 저장되어있는 경우 -> 애초에 list에서 제외 시켜 보여주지 않음
        val i:Int=0
        for(diary in diaryList){
            if(diary.content!=null){
                exceptNullList.add(diary)
            }
        }
        myadapter=MyAdapter(exceptNullList.reversed().toMutableList())
        diaryListBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(this,
            LinearLayoutManager.VERTICAL)
        )
        diaryListBinding.recyclerView.adapter=myadapter // 나중에 일기를 쓰고 나서 데이더가 업데이트 되었을때 오늘분의 일기가 표시될지 고려해봐야됨
        diaryListBinding.closeButton.setOnClickListener {
            onBackPressed()
        }
        diaryListBinding.addButton.setOnClickListener {
            val intent= Intent(this, WritingActivity::class.java)
            resultLauncher?.launch(intent)
        }
    }
    private fun update()
    {
        myadapter!!.diaryList[0]=database.getDiary(myadapter!!.diaryList[0].date)[0]
        myadapter!!.notifyItemChanged(0)
    }
}