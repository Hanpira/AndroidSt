package com.example.item;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Plant3 extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    TextView aboutPlant3;
    EditText datePlant;
    DatePickerDialog.OnDateSetListener dateSetDatePlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        aboutPlant3 = (TextView) findViewById(R.id.textAboutPlant2);

        Bundle args = getIntent().getExtras();
        aboutPlant3.append("\n Вид: ");
        aboutPlant3.append(args.getString("editSpecies"));
        aboutPlant3.append("\n Тип: ");
        aboutPlant3.append(args.getString("TypePlant"));
        aboutPlant3.append("\n Сорт: ");
        aboutPlant3.append(args.getString("Sort"));
        aboutPlant3.append("\n Количество");
        aboutPlant3.append(args.getString("Number"));

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // datepicker
        datePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Plant3.this, dateSetDatePlant, year, month, day).show();
                datePlant.setTextColor(Color.BLACK);
            }
        });

        dateSetDatePlant = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                datePlant.setText(date);
            }
        };
    }

    public void OnImageGet(View view)
    {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(camera_intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        // BitMap is data structure of image file which store the image in memory
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        // Set the image in imageview for display
                        ImageView imageView=(ImageView)findViewById(R.id.imageView);
                        imageView.setImageBitmap(photo);
                    }
            }
        });

    public void onBackRegisterPlant2(View view) {
        Log.i("nextStep", "onRegisterPlant: ");
        Intent intent = new Intent(this, Plant2.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra("Date", datePlant.getText().toString());
        startActivity(intent);
    }

    public void onRegisterPlant3(View view) {
        Log.i("backStep", "onRegisterPlant: ");
        Intent intent = new Intent(this, Result.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra("Date", datePlant.getText().toString());
        startActivity(intent);
    }
}