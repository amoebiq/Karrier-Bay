package Utilities;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by nirmal.ku on 3/3/2017.
 */

public class Utility {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isNull(String[] arr) {
        boolean error = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null || arr[i].isEmpty()) {
                error = true;
            }
        }
        return error;
    }

    public static void setListenerToHideKeyboard(final Activity activity, View view) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(view instanceof EditText)) {
                    Utility.hideKeyboard(activity);

                }
                return false;
            }
        });
    }

    public static String displayDateTime(String dateAndTime) {
        if (dateAndTime == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(df.parse(dateAndTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(calendar.get(Calendar.DATE) + " - " + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + "-" +
                calendar.get(Calendar.YEAR) + " , " + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE));
        return calendar.get(Calendar.DATE) + " - " + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + "-" +
                calendar.get(Calendar.YEAR) + " , " + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " " + calendar.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.US);

    }
}
