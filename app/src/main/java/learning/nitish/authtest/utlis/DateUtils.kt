/*
 * Created by Nitish Singh on 29/7/17 4:42 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.utlis

import android.util.Log
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */


/**
 * A DateUtility class to convert time to display social format
 */
object DateUtils {

    private val TAG = "NITISH"
    private val DATE_FORMAT_TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"

    private fun convertToDate(twitterDate: String): Date? {
        val sf = SimpleDateFormat(DATE_FORMAT_TWITTER, Locale.ENGLISH)
        try {
            return sf.parse(twitterDate)
        } catch (e: ParseException) {
            Log.e(TAG, e.message)
            return null
        }

    }

    private fun getRelativeTime(date: Date?): String {
        if (date != null) {
            val p = PrettyTime()
            return p.format(date)
        } else {
            return ""
        }
    }


    /**
     * A simple method to convert datetime in social format
     */
    fun getTwitterRelativeTime(twitterDate: String): String {
        return getRelativeTime(convertToDate(twitterDate))
    }
}
