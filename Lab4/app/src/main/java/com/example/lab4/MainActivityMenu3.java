package com.example.lab4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivityMenu3 extends AppCompatActivity {
    private EditText editTextField3;
    private EditText editTextField4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu3);
        editTextField3 = findViewById(R.id.editTextField3);
        editTextField4 = findViewById(R.id.editTextField4);
        registerForContextMenu(editTextField3);
        // для долгого нажатия на EditText
        editTextField4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v);
                return true;
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.pop_fill_field) {
                    editTextField4.setText("Anna");
                    return true;
                } else if (item.getItemId() == R.id.pop_clear_field) {
                    editTextField4.setText("");
                    return true;
                } else if (item.getItemId() == R.id.pop_check_data) {
                    checkData();
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void checkData() {
        String input = editTextField4.getText().toString();
        if (input.matches(".*[0-9!@#$%^&*()_+=<>?].*")) {
            showToast("Данные некорректны. Введите данные исключая символы и цифры");
        }
    if (input.matches("Anna")){//ввод данного имени для перехода к найстройке даты и времени
        showAlertDialog();
    }else {
            showToast("Данные корректны");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onClickReturnMP(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.pop_fill_field) {
            editTextField3.setText("Anna");
            return true;
        } else if (item.getItemId() == R.id.pop_clear_field) {
            editTextField3.setText("");
            return true;
        } else if (item.getItemId() == R.id.pop_check_data) {
            checkField();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void checkField() {
        String input = editTextField3.getText().toString();
        if (input.matches("[a-zA-Zа-яА-ЯёЁ]{1,20}")) { // Проверка на буквы и длину
            showSnackBar("Данные корректные");
        } else {
            showSnackBar("Данные некорректные. Используйте только буквы и максимум 20 символов.");
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(editTextField3, message, Snackbar.LENGTH_SHORT).show();
    }
    private void showAlertDialog() {
        // Создаем макет для диалога
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Установка даты и времени")
                .setView(dialogView) // Устанавливаем кастомный макет
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Действие при нажатии кнопки "Да" - переход на MainActivity4
                        Intent intent = new Intent(MainActivityMenu3.this, MainActivityMenu4.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Действие при нажатии кнопки "Нет" - закрыть диалог
                        dialog.dismiss();
                    }
                });

        //Создать и показать диалог
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

