package business;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

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

    public static boolean isNetworkAvailable(AppCompatActivity context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

