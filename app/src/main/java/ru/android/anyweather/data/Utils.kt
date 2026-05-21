package ru.android.anyweather.data

import android.util.Log
import androidx.annotation.StringRes
import ru.android.anyweather.R

abstract class Utils {
    companion object{
        private const val LOG_TAG = "MYLOGGER"

        fun log(message : String){
            Log.w(LOG_TAG,message)
        }

        @StringRes
        fun parseWMO(code : String) : Int =
            when(code){
                "0" -> R.string.wmo0
                "1" -> R.string.wmo1
                "2" -> R.string.wmo2
                "3" -> R.string.wmo3
                "45" -> R.string.wmo45
                "48" -> R.string.wmo48
                "51" -> R.string.wmo51
                "53" -> R.string.wmo53
                "55" -> R.string.wmo55
                "56" -> R.string.wmo56
                "57" -> R.string.wmo57
                "61" -> R.string.wmo61
                "63" -> R.string.wmo63
                "65" -> R.string.wmo65
                "66" -> R.string.wmo66
                "67" -> R.string.wmo67
                "71" -> R.string.wmo71
                "73" -> R.string.wmo73
                "75" -> R.string.wmo75
                "77" -> R.string.wmo77
                "80" -> R.string.wmo80
                "81" -> R.string.wmo81
                "82" -> R.string.wmo82
                "85" -> R.string.wmo85
                "86" -> R.string.wmo86
                "95" -> R.string.wmo95
                "96" -> R.string.wmo96
                "99" -> R.string.wmo99
                else -> R.string.wmo_undefined
            }

        @StringRes
        fun parseMonth(monthNum : Int) : Int =
            when(monthNum){
                1 -> R.string.month1
                2 -> R.string.month2
                3 -> R.string.month3
                4 -> R.string.month4
                5 -> R.string.month5
                6 -> R.string.month6
                7 -> R.string.month7
                8 -> R.string.month8
                9 -> R.string.month9
                10 -> R.string.month10
                11 -> R.string.month11
                12 -> R.string.month12
                else -> R.string.month_undefined
            }
    }
}