package Utilities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import RetroGit.ApiClient;
import RetroGit.ApiInterface;

/**
 * Created by nirmal.ku on 11/7/2016.
 */
public class BaseActivity extends Activity {

    protected Typeface mTfRegular;
    protected Typeface mTfBold;
    protected Typeface mTfSemiBold;
    protected ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        mTfSemiBold = Typeface.createFromAsset(getAssets(), "OpenSans-Semibold.ttf");

        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void dateClick(View view) {
        final EditText et = (EditText) view;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this,
                new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0,
                                                  int arg1, int arg2, int arg3) {

                                et.setText(arg1 + "-" + arg2 + 1 + "-" + arg3);
                            }
                        }, year, month, day);
    }

    public void timeClick(View view) {
        final EditText et = (EditText) view;
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
