package vn.tiki.android.androidhometest.util

import java.util.ArrayList
import java.util.Collections
import java.util.Date
import java.util.EnumSet
import java.util.LinkedHashMap
import java.util.concurrent.TimeUnit

object TimeUtil {

    fun computeDiff(date1: Date, date2: Date): Map<TimeUnit, Long> {
        val diffInMillies = date2.time - date1.time
        val units = ArrayList(EnumSet.allOf(TimeUnit::class.java))
        Collections.reverse(units)
        val result = LinkedHashMap<TimeUnit, Long>()
        var milliesRest = diffInMillies
        for (unit in units) {
            val diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS)
            val diffInMilliesForUnit = unit.toMillis(diff)
            milliesRest = milliesRest - diffInMilliesForUnit
            result[unit] = diff
        }
        return result
    }
}
