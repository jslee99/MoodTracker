package com.mobileprograming.moodtracker.ui.calendar

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityCalendarBinding
import com.mobileprograming.moodtracker.ui.detail.DetailActivity
import com.mobileprograming.moodtracker.ui.diarylist.DiaryListActivity
import com.mobileprograming.moodtracker.ui.setting.SettingActivity
import com.mobileprograming.moodtracker.ui.writing.TestWritingActivity
import com.mobileprograming.moodtracker.ui.writing.WritingActivity
import com.mobileprograming.moodtracker.util.IntentKey
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

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
        initBtnListener()
        initMoodImageListener()

        initTestWritingListener()

        setMonthYearTextView(localDate)
        setRecyclerView(localDate)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setRecyclerView(localDate)
    }

    private fun initDB() {
        myDBHelper = MyDBHelper(this)
    }

    private fun initList() {
        // 아래 함수를 통해 데이터베이스에서 다이어리 리스트 호출 가능
        // diaryList = myDBHelper.getAllDiary()
        // 테스트용 다이어리 리스트입니다.
//        diaryList = listOf(
//            Diary(0,0,"test 0",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
//            Diary(1,1,"test 1",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
//            Diary(2,2,"test 2",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()))
    }

    private fun intentSettingActivity(){
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun intentDiaryListActivity(){
        val intent = Intent(this, DiaryListActivity::class.java)
        startActivity(intent)
    }

    // 무드를 입력받아 WritingActivity 이동
    private fun intentWriteDiary(mood:Int){
        val intent = Intent(this, WritingActivity::class.java)
        intent.putExtra(IntentKey.MOOD_KEY, mood)
        startActivity(intent)
    }

    // Diary 객체를 받아서 DetailActivity 이동
//    private fun intentDetail(diary: Diary){
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra(IntentKey.DIARY_KEY, diary)
//        startActivity(intent)
//    }

    private fun intentDetail(ldate : Long){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(IntentKey.DIARY_KEY, ldate)
        startActivity(intent)
    }

    private fun initMoodImageListener(){
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
                if(cellData.date != (-1).toLong() && cellData.mood != -1)
                    intentDetail(cellData.date)
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
        //selectedDate의 month, year을 string형태로 변환
        val monthF = DateTimeFormatter.ofPattern("MM")
        val yearF = DateTimeFormatter.ofPattern("yyyy")
        val monthStr = selectedDate.format(monthF)
        val yearStr = selectedDate.format(yearF)
        for(i in 0..41){
            val DaysInMonthArray = daysInMonthArray(selectedDate)
            var ldate : Long
            var mood : Int
            //content와 image는 calendarView에서 필요x dummy정보
            var content = ""
            val image = null
            if(DaysInMonthArray[i] != -1){//그 달에 해당하는 날짜가 있는 cell
                val sdf = SimpleDateFormat("yyyy.MM.dd")
                var dayStr = DaysInMonthArray[i].toString()
//                if(dayStr.length == 1){
//                    dayStr = "0" + dayStr
//                }
//                val formatStr = binding.activityCalendarYearText.text.toString() + "." + binding.activityCalendarMonthText.text.toString() + "." + dayStr
                val formatStr = yearStr + "." + monthStr + "." + dayStr
                val date = sdf.parse(formatStr)
                ldate = date.time
                val diaryList = myDBHelper.getDiary(ldate)
                if(diaryList.size > 0){//그 날짜에는 일기를 작성하였다.
                    mood = diaryList[0].mood
                }else{//해당하는 데이터를 찾을수 없다 즉, 그날짜에는 일기를 작성하지 않았다.
                    mood = -1
                }
            }else{//그 달에 해당하는 날짜가 없는 cell
                ldate = -1
                mood = -1
            }
//            val image = ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()
            adapter.DiaryList.add(Diary(ldate, mood, content, image))
        }
        adapter.notifyItemRangeChanged(0, 42)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initBtnListener(){
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
        binding.activityCalendarListBtn.setOnClickListener {
            intentDiaryListActivity()
        }
        binding.activityCalendarSettingBtn.setOnClickListener {
            intentSettingActivity()
        }
    }

    private fun initTestWritingListener(){
        binding.testWriting.setOnClickListener {
            val intent = Intent(this, TestWritingActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }
}