package com.example.lab7_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText signupEmail, signupPassword;
    private Button signupBtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupEmail = (EditText)findViewById(R.id.editSignupEmail);
        signupPassword = (EditText) findViewById(R.id.editSignupPassw);
        signupBtn = (Button) findViewById(R.id.btnSignUp);
        auth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString();
                String pas = signupPassword.getText().toString();

                if (user.isEmpty()){
                    signupEmail.setError("You should enter the email");
                }
                if (pas.isEmpty()){
                    signupPassword.setError("You should enter the password");
                } else {
                    auth.createUserWithEmailAndPassword(user,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(SignUp.this, SignIn.class));
                                Toast.makeText(SignUp.this, "Registration is successful. Now Login", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUp.this, "Registration Failed. Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    public void OnLogin(View view) {
        Intent intent = new Intent(this, SignIn.class);
        Toast.makeText(this, "Going to Login", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
