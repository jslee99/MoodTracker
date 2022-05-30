package com.mobileprograming.moodtracker.ui.writing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import com.mobileprograming.moodtracker.R
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityTestWritingBinding
import com.mobileprograming.moodtracker.databinding.ActivityWritingBinding
import com.mobileprograming.moodtracker.ui.calendar.CalendarActivity
import com.mobileprograming.moodtracker.util.IntentKey
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate

class TestWritingActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestWritingBinding
    lateinit var myDBHelper: MyDBHelper

    // 테스트용 초기 mood 값
    var mood = 0

    //사진 관련 variable
    private val IMAGE_CHOOSE = 1;
    private val PERMISSION_CODE = 2;
    private var imageUri: Uri? = null
    var byteArray: ByteArray? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide action bar(title bar)
        supportActionBar?.hide()

        binding = ActivityTestWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDB()
        initLayout()

        // 아래 함수를 통해 무드를 전달받습니다.
        mood = intent.getIntExtra(IntentKey.MOOD_KEY,0)
        emoticonSelector(mood)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initLayout() {
        with(binding){

            //back button
            backButton.setOnClickListener {
                finish()
            }

            //사진 추가 button
            diaryPhoto.setOnClickListener {
                showChooseImageDialog()
            }

            //기분 이미지 관리함
            imageView.setOnClickListener {
                mood = 0
                emoticonSelector(mood)
            }
            imageView2.setOnClickListener {
                mood = 1
                emoticonSelector(mood)
            }
            imageView3.setOnClickListener {
                mood = 2
                emoticonSelector(mood)
            }
            imageView4.setOnClickListener {
                mood = 3
                emoticonSelector(mood)
            }
            imageView5.setOnClickListener {
                mood = 4
                emoticonSelector(mood)
            }

            //데이터베이스에 저장하는 버튼
            addButton.setOnClickListener {
                insert()
            }

            //취소 버튼
            cancelButton.setOnClickListener {
                onBackPressed()
            }
        }

    }

    //선태한 기분 크기 조정하는 함수
    private fun emoticonSelector(mood: Int){

        val selected: Int = (2 * resources.displayMetrics.density).toInt()
        val notSelected: Int = (5 * resources.displayMetrics.density).toInt()

        with(binding) {
            when (mood) {
                0 -> {
                    imageView.setPadding(selected)
                    imageView2.setPadding(notSelected)
                    imageView3.setPadding(notSelected)
                    imageView4.setPadding(notSelected)
                    imageView5.setPadding(notSelected)
                }
                1 -> {
                    imageView.setPadding(notSelected)
                    imageView2.setPadding(selected)
                    imageView3.setPadding(notSelected)
                    imageView4.setPadding(notSelected)
                    imageView5.setPadding(notSelected)
                }
                2 -> {
                    imageView.setPadding(notSelected)
                    imageView2.setPadding(notSelected)
                    imageView3.setPadding(selected)
                    imageView4.setPadding(notSelected)
                    imageView5.setPadding(notSelected)
                }
                3 -> {
                    imageView.setPadding(notSelected)
                    imageView2.setPadding(notSelected)
                    imageView3.setPadding(notSelected)
                    imageView4.setPadding(selected)
                    imageView5.setPadding(notSelected)
                }
                4 -> {
                    imageView.setPadding(notSelected)
                    imageView2.setPadding(notSelected)
                    imageView3.setPadding(notSelected)
                    imageView4.setPadding(notSelected)
                    imageView5.setPadding(selected)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveTime(): Long {
        val localDate = LocalDate.now()
        val y = localDate.year
        val m = localDate.monthValue
        val d = localDate.dayOfMonth
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val formatStr = y.toString() + "." + m.toString() + "." + d.toString()
        val date = sdf.parse(formatStr)
        val ldate = date.time
        return ldate
    }


    private fun initDB() {
        myDBHelper = MyDBHelper(this)
    }

    // 데이터베이스에 diary 추가 예제 코드일 뿐입니다.
    // 삽입 방식이 이해되셨다면 지우셔도 됩니다.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insert(){

        // 이미지 비트맵 가져오기 ( 예시, R.drawable.happy_1 )
        //val bitmap = ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toByteA()

        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val formatStr = binding.editYear.text.toString() + "." + binding.editMonth.text.toString() + "." + binding.editDay.text.toString()
        val date = sdf.parse(formatStr)
        val time = date.time

        // 다이어리 객체 생성
        val diary = Diary(time, mood, binding.diaryText.text.toString(), byteArray)

        myDBHelper.insert(diary)
//        val intent = Intent(this, CalendarActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//        startActivity(intent)
        finish()
    }


    private fun showChooseImageDialog() {
        val dialog = android.app.AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(
            R.layout.dialog_choose_image,
            findViewById(R.id.dialog_choose_image_container)
        )
        dialog.setView(view)

        val chooseImageDialog = dialog.create()
        val chooseImageButton: LinearLayout = view.findViewById(R.id.choose_image)

        chooseImageDialog.window?.run {
            setBackgroundDrawable(ColorDrawable(0))
            chooseImageDialog.show()
        }

        chooseImageButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else{
                    chooseImageGallery();

                }
            }else{
                chooseImageGallery();
            }
            chooseImageDialog.dismiss()
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_CHOOSE -> if (resultCode == RESULT_OK) {
                imageUri = data?.data
                binding.diaryPhoto.setImageURI(imageUri)
                //Uri -> ByteArray
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                byteArray = stream.toByteArray()

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}