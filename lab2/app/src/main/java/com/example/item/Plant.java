package com.example.item;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Plant extends AppCompatActivity {

    private TextView mInfoTextView;
    Button btn_start_Info;
    EditText editSpecies;
    RadioButton typeTree, typeBush, typeGrass;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_start_Info = (Button) findViewById(R.id.btnStartInfo);
        editSpecies = (EditText) findViewById(R.id.editTextPlantSpecies);
        typeTree = (RadioButton) findViewById(R.id.radioBtnPlantType1);
        typeBush =(RadioButton) findViewById(R.id.radioBtnPlantType2);
        typeGrass =(RadioButton) findViewById(R.id.radioBtnPlantType3);
        radioGroup = (RadioGroup) findViewById(R.id.typeVariety);
        mInfoTextView = (TextView) findViewById(R.id.textViewCheck);

        Bundle args = getIntent().getExtras();
        if(args != null) {
            editSpecies.setText(args.getString("editSpecies"));
            mInfoTextView.setText(args.getString("TypePlant"));
        }

        typeTree.setOnClickListener(radioClickListener);
        typeBush.setOnClickListener(radioClickListener);
        typeGrass.setOnClickListener(radioClickListener);
    }

    public void onRegisterPlant(View view) {
        Log.i("nextStep", "onRegisterPlant: ");

        if (editSpecies == null) {
            Toast.makeText(this, "Введите вид растения",Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, Plant2.class);
            intent.putExtras(getIntent().getExtras());
            intent.putExtra("editSpecies", editSpecies.getText().toString());
            intent.putExtra("TypePlant", mInfoTextView.getText().toString());
            startActivity(intent);
        }
    }

    View.OnClickListener radioClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rbtn = (RadioButton) v;
            String typePlant = "";
            if (rbtn == typeTree){
               typePlant = "Дерево";
            } else if (rbtn == typeBush) {
                typePlant = "Куст";
            } else if (rbtn == typeGrass) {
                typePlant = "Трава";
            }
            mInfoTextView.setText(typePlant);
        }
    };

    public void OnMainBtn(View view) {
        Log.i("backStep", "onRegisterPlant: ");
        Intent intent = new Intent(this, Plant3.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra("TypePlant", mInfoTextView.getText().toString());
        intent.putExtra("editSpecies", editSpecies.getText().toString());
        startActivity(intent);
    }
}