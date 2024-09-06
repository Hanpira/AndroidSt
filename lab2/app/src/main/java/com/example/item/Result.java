package com.example.item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {

    TextView textPlantResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textPlantResult = (TextView) findViewById(R.id.textAboutPlantResult);
    }

    public void onBackRegisterPlant(View view) {
        Log.i("backStep", "onRegisterPlant3: ");
        Intent intent = new Intent(this, Plant.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }


    public void OnEmailButton(View view) {
        Bundle args= getIntent().getExtras();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{args.getString("Email","test@gmail.com")});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Email from "+ args.getString("Name"));
        sendIntent.putExtra(Intent.EXTRA_TEXT, textPlantResult.getText().toString());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void OnPhoneDiag (View view) {
        Bundle args = getIntent().getExtras();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("phone:" + args.getString("Phone")));
        startActivity(intent);
    }
}