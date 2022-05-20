package com.mobileprograming.moodtracker.ui.calendar

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.GridLayoutManager
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityCalendarBinding
import com.mobileprograming.moodtracker.ui.detail.DetailActivity
import com.mobileprograming.moodtracker.ui.writing.WritingActivity
import com.mobileprograming.moodtracker.util.IntentKey
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.time.Clock.system
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var diaryList : List<Diary>
    lateinit var localDate : LocalDate
    lateinit var adapter : CalendarViewAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localDate = LocalDate.now()
        initDB()
        initList()

        initRecyclerVIew()
        initMonthBtnListener()
        initMoodImagetListener()

        setMonthYearTextView(localDate)
        setRecyclerView(localDate)
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

    private fun initMoodImagetListener(){
        binding.apply {
            activityCalendarMood0.setOnClickListener {
                intentWriteDiary(0)
            }
            activityCalendarMood1.setOnClickListener {
                intentWriteDiary(1)
            }
            activityCalendarMood2.setOnClickListener {
                intentWriteDiary(2)
            }
            activityCalendarMood3.setOnClickListener {
                intentWriteDiary(3)
            }
            activityCalendarMood4.setOnClickListener {
                intentWriteDiary(4)
            }
        }
    }

    private fun initRecyclerVIew(){
        //empty adapter 연결, layoutmanager 설정
        adapter = CalendarViewAdapter(ArrayList<Diary>())
        binding.calendarRecylcerView.adapter = adapter
        adapter.itemClickListener = object : CalendarViewAdapter.onItemClickListener{
            override fun onItemClick(cellData: Diary) {
                intentDetail(cellData)
            }
        }
        binding.calendarRecylcerView.layoutManager = GridLayoutManager(this, 7)
    }

    //selectedDate의 month에 맞춰서 daysInMonthArray(size : 7 * 6 = 42)를 반환, 해당하는 month에 없는 날짜면 -1을 넣는다. 해당하는 month에 있는 날짜면 그 날짜를 넣음
    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(selectedDate : LocalDate) : ArrayList<Int>{
        val DaysInMonthArray = ArrayList<Int>()
        val yearMonth = YearMonth.from(selectedDate)

        //현재 month의 길이
        val daysInMonth = yearMonth.lengthOfMonth()
        //현재 month의 첫번째날을 가지고 온후에 그 날이  월요일이면 1, 화요일이면 2.....을 반환하여 dayOfWeek에 저장
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for(i in 1..42){
            if(dayOfWeek <= i && i < dayOfWeek + daysInMonth){
                DaysInMonthArray.add(i - dayOfWeek + 1)
            }else{
                DaysInMonthArray.add(-1)
            }
        }
        return DaysInMonthArray
    }

    //year와 month textview를 selectedDate에 따라 변경
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthYearTextView(selectedDate : LocalDate){
        val monthF = DateTimeFormatter.ofPattern("MM")
        val yearF = DateTimeFormatter.ofPattern("yyyy")
        binding.activityCalendarMonthText.text = selectedDate.format(monthF)
        binding.activityCalendarYearText.text = selectedDate.format(yearF)
    }

    //selectedDate에 따라 calendar recycler view 변경
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerView(selectedDate: LocalDate){
        //set adapter.DiaryListfd
        adapter.DiaryList.clear()
        for(i in 0..41){

            val DaysInMonthArray = daysInMonthArray(selectedDate)
            var ldate : Long
            if(DaysInMonthArray[i] != -1){
                val sdf = SimpleDateFormat("yyyy.MM.dd")
                var dayStr = DaysInMonthArray[i].toString()
                if(dayStr.length == 1){
                    dayStr = "0" + dayStr
                }
                val formatStr = binding.activityCalendarYearText.text.toString() + "." + binding.activityCalendarMonthText.text.toString() + "." + dayStr
                val date = sdf.parse(formatStr)
                ldate = date.time
            }else{
                ldate = -1
            }
            //DB에서 mood정보 가지고 와야한다.
            val mood = 0
            val content = ""
            val image = null
            adapter.DiaryList.add(Diary(ldate, mood, content, image))
        }
        adapter.notifyItemRangeChanged(0, 42)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initMonthBtnListener(){
        binding.activityCalendarPrev.setOnClickListener {
            localDate = localDate.minusMonths(1)
            setMonthYearTextView(localDate)
            setRecyclerView(localDate)
        }
        binding.activityCalendarNext.setOnClickListener {
            localDate = localDate.plusMonths(1)
            setMonthYearTextView(localDate)
            setRecyclerView(localDate)
        }
    }
}