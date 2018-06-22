package vn.tiki.android.androidhometest.util;

public class StringUtil {

    public static String convertPrice(double price, String unit){
        int decimal = (int) price / 1000;
        String mod = String.valueOf((int) (price % 1000));
        if(mod.equals("0")){
            mod = "000";
        }
        return decimal + "." + mod + " " + unit;
    }
}
