package business;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by paolo on 02/12/2017.
 */

public class Util {

    //ritorna il giorno della settimana su 3 caratteri
    public static String getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        // 3 letter name form of the day
        return new SimpleDateFormat("EE", Locale.ITALIAN).format(date.getTime()).toLowerCase();
    }
}

