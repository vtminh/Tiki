package vn.tiki.android.androidhometest.util

object StringUtil {

    fun convertPrice(price: Double, unit: String): String {
        val decimal = price.toInt() / 1000
        var mod = (price % 1000).toInt().toString()
        if (mod == "0") {
            mod = "000"
        }
        return decimal.toString() + "." + mod + " " + unit
    }
}
