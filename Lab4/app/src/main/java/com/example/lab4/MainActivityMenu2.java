package com.example.lab4;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivityMenu2 extends AppCompatActivity {
    private EditText editTextField1;
    private TextView editTextField2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu2);
        editTextField1 = findViewById(R.id.editTextField1);
        editTextField2 = findViewById(R.id.editTextField2);

        registerForContextMenu(editTextField1);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Toast при переходе с первой страницы
        Toast.makeText(this, "Перешли на вторую страницу", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.pop_fill_field) {
            editTextField1.setText("test@iba.by");
            return true;
        } else if (item.getItemId() == R.id.pop_clear_field) {
            editTextField1.setText("");
            return true;
        } else if (item.getItemId() == R.id.pop_check_data) {
            String email = editTextField1.getText().toString();
            if (isValidEmail(email)) {
                showSnackBar("Данные корректные");
            } else {
                showSnackBar("Данные не соответствуют формату электронной почты");
            }
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showSnackBar(String message) {
        Snackbar.make(editTextField1, message, Snackbar.LENGTH_SHORT).show();
    }


    public void onClickReturnMP(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}