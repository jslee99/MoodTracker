package com.mobileprograming.moodtracker.ui.setting

data class AlarmDisplayModel(
    val hour:Int,
    val minute:Int,
    var onOff:Boolean
){
    fun makeDataForDB():String{
        return "$hour:$minute"
    }

    val timeText:String
        get() = "${"%02d".format(hour)}:${"%02d".format(minute)}"
}