package com.example.lab6_1;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    EditText editText, editTextAdSql;
    TextView textViewShredPref, textViewInternal, textViewExternal, textViewSQL;
    SharedPreferences setting;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText = findViewById(R.id.editTextHere);
        editTextAdSql = findViewById(R.id.editTextHereSql);
        textViewShredPref =findViewById(R.id.textViewShredPref);
        textViewInternal= findViewById(R.id.textViewInternal);
        textViewExternal= findViewById(R.id.textViewExternal);
        textViewSQL = findViewById(R.id.textViewSQL);
        setting = getSharedPreferences("key", MODE_PRIVATE);
        database = getBaseContext().openOrCreateDatabase("Lab6DataBase.db", MODE_PRIVATE, null);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database.execSQL("CREATE TABLE IF NOT EXISTS MEMOSQL(TEXT TEXT, YEAR INTEGER)");
//        database.execSQL("INSERT OR IGNORE INTO MEMOSQL VALUES ('IBA',2021),('Study',2024)");
//        database.execSQL("DROP TABLE MEMOSQL");

    }

    public void WriteSharedPref(View view) {
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("key", editText.getText().toString());
        editor.apply();
    }

    public void ReadSharedPref(View view) {
        String temp = setting.getString("key", "default");
        textViewShredPref.setText(temp);
    }

    public void WriteInternal(View view) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("lab6file.txt", MODE_PRIVATE);
            String textToWrite = editText.getText().toString(); // Получаем текст из EditText
            fos.write(textToWrite.getBytes()); // Записываем текст в файл
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ReadInternal(View view) throws IOException {
        FileInputStream fin = openFileInput("lab6file.txt");
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        String text = new String(bytes);
        textViewInternal.setText(text);
    }

    public void WriteExternal(View view) throws Exception {
        File file = new File(getExternalFilesDir(null), "lab6_1_external.js");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(editText.getText().toString().getBytes(StandardCharsets.UTF_8));
        fos.flush();
    }

    public void ReadExternal(View view) throws Exception {
        File file = new File(getExternalFilesDir(null), "lab6_1_external.js");
        FileInputStream fin = new FileInputStream(file);
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        String text = new String(bytes);
        textViewExternal.setText(text);
        Log.i("External", text);
    }

    public void WriteSql(View view) {
        String text = editText.getText().toString();
        int year = Integer.parseInt(editTextAdSql.getText().toString());

        database.execSQL("INSERT INTO MEMOSQL (TEXT, YEAR) VALUES (?, ?)", new Object[]{text, year});
    }

    public void ReadSql(View view) {
        Cursor cursor = database.rawQuery("SELECT * FROM MEMOSQL", null);
        StringBuilder data = new StringBuilder();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("TEXT"));
            int age = cursor.getInt(cursor.getColumnIndex("YEAR"));
            data.append("Text: ").append(name).append(", Year: ").append(age).append("\n");
        }

        textViewSQL.setText(data.toString());
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close(); // Закрываем базу данных
        }
    }

    public void deleteData(View v) {
        String text = editText.getText().toString();

        database.execSQL("DELETE FROM MEMOSQL WHERE TEXT = ?", new Object[]{text});
    }
    public void UpdateSql(View view) {
        String text = editText.getText().toString();
        int newYear = Integer.parseInt(editTextAdSql.getText().toString());
        database.execSQL("UPDATE MEMOSQL SET YEAR = ? WHERE TEXT = ?", new Object[]{newYear, text});
    }

    public void DeleteSql(View view) {
        database.execSQL("DELETE FROM MEMOSQL");
        textViewSQL.setText("");
    }


}