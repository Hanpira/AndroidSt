package com.example.item;

import android.content.Intent;
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

public class Plant2 extends AppCompatActivity {

    TextView plantInfo;
    EditText editSort, editNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        plantInfo = (TextView) findViewById(R.id.textAboutPlant);
        editSort = (EditText) findViewById(R.id.editTextPlantSort);
        editNumber = (EditText) findViewById(R.id.editTextNumber);

        Bundle args = getIntent().getExtras();
        plantInfo.append("\n Вид: ");
        plantInfo.append(args.getString("editSpecies"));
        plantInfo.append("\n Тип: ");
        plantInfo.append(args.getString("TypePlant"));

        editSort.setText(args.getString("Sort", ""));
        editNumber.setText(args.getString("Number", ""));
    }

    public void onBackRegisterPlant(View view) {
        Log.i("nextStep", "onRegisterPlant: ");
        Intent intent = new Intent(this, Plant.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra("Sort", editSort.getText().toString());
        intent.putExtra("Number", editNumber.getText().toString());
        startActivity(intent);
    }

    public void onRegisterPlant2(View view) {
        Log.i("nextStep", "onRegisterPlant: ");
        Intent intent = new Intent(this, Plant3.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra("Sort", editSort.getText().toString());
        intent.putExtra("Number", editNumber.getText().toString());
        startActivity(intent);
    }
}