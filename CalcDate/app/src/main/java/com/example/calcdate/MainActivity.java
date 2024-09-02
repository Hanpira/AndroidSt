package com.example.calcdate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    Button btn_calculate;
     RadioButton rbtn_today;
    EditText editDateBirth, editDateEnd;
    TextView tvResult;

    DatePickerDialog.OnDateSetListener dateSetDateStart, dateSetDateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variables
        editDateBirth = (EditText) findViewById(R.id.editDateBirth);
        editDateEnd = (EditText) findViewById(R.id.editDateEnd);
        //rbtn_today = (RadioButton) findViewById(R.id.radioBtnToday);

        btn_calculate = (Button) findViewById(R.id.btn_calculate);
        tvResult = (TextView) findViewById(R.id.tv_result);

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);


        editDateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, dateSetDateStart, year, month, day).show();
            }
        });

        dateSetDateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                month = month + 1;
                String date = day + "/" + month + "/" + year;
                editDateBirth.setText(date);
            }
        };

        editDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, dateSetDateEnd, year, month, day).show();
            }
        });
        dateSetDateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                editDateEnd.setText(date);
            }
        };

        // action to be performed when calculate button is clicked
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // converting the inputted date to string
                String dateBirth = editDateBirth.getText().toString();
                String dateEnd = editDateEnd.getText().toString();

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1=simpleDateFormat1.parse(dateBirth);
                    Date date2=simpleDateFormat1.parse(dateEnd);
                    long startDate = date1.getTime();
                    long endDate = date2.getTime();

                    // condition
                    if (startDate <= endDate) {
                        long time_difference = endDate - startDate;

                        // Calculate time difference in days using TimeUnit class
                        //long days_difference = TimeUnit.MILLISECONDS.toDays(time_difference) % 365;
                        // Calculate time difference in years using TimeUnit class
                        //long years_difference = TimeUnit.MILLISECONDS.toDays(time_difference) / 365l;
                        // Calculate time difference in seconds using TimeUnit class
                        //long seconds_difference = TimeUnit.MILLISECONDS.toSeconds(time_difference) % 60;
                        // Calculate time difference in minutes using TimeUnit class
                        long minutes_difference = TimeUnit.MILLISECONDS.toMinutes(time_difference) % 60;
                        // Calculate time difference in hours using TimeUnit class
                        long hours_difference = TimeUnit.MILLISECONDS.toHours(time_difference) % 24;

                        org.joda.time.Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());
                        int years_difference = period.getYears();
                        int months_difference = period.getMonths();
                        int days_difference = period.getDays();

                        tvResult.setText(years_difference + "лет " + months_difference + " месяцев " + days_difference + " дней, " + hours_difference + " часов " + minutes_difference + " минут.");

                    } else {
                        // show message
                        Toast.makeText(MainActivity.this, "Дата рождения должна быть раньше конечной даты!", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
