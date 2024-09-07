package com.example.lab4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivityMenu4 extends AppCompatActivity {

    private TextView textDateTime;
    private Button buttonSelectDateTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu4);

        textDateTime = findViewById(R.id.textDateTime);
        buttonSelectDateTime = findViewById(R.id.buttonSelectDateTime);

        buttonSelectDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // После выбора даты, показываем TimePicker
                    showTimePickerDialog(selectedYear, selectedMonth, selectedDay);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    // Обновляем TextView с выбранной датой и временем
                    String dateTime = String.format("%02d/%02d/%04d %02d:%02d", day, month + 1, year, selectedHour, selectedMinute);
                    textDateTime.setText(dateTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }
    public void backToMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    }
