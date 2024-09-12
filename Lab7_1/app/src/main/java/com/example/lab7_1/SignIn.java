package com.example.lab7_1;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignIn extends AppCompatActivity {

    private EditText signinEmail, signinPassword;
    private FirebaseAuth auth;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signinEmail= (EditText) findViewById(R.id.signinEmail);
        signinPassword= (EditText) findViewById(R.id.signinPassword);
        auth = FirebaseAuth.getInstance();

        //region google code (though googleSignInClient is deprecated
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(SignIn.this, options);

    }

    public void OnRegister(View view) {
        Intent intent = new Intent(this, SignUp.class);
        Toast.makeText(this, "Going to Sign Up Page", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void OnLogin(View view) {
        String email = signinEmail.getText().toString();
        String pas = signinPassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pas.isEmpty()){
                auth.signInWithEmailAndPassword(email,pas).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignIn.this,"Login is successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                        finish();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this,"Login is successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            signinEmail.setError("Please, enter correctEmail");
        }

    }


    public void OnForgotPassword(View view) {
        Toast.makeText(SignIn.this,"ForgotPassword? Let's try to reset", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot,null);
        EditText emailForgot = dialogView.findViewById(R.id.EmailForgotPas);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnResetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailReset = emailForgot.getText().toString();

                if (userEmailReset.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(userEmailReset).matches()) {
                    Toast.makeText(SignIn.this,"Enter correct email", Toast.LENGTH_SHORT).show();

                    return;
                }
                auth.sendPasswordResetEmail(userEmailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this,"Check email", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(SignIn.this,"Sending failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        if (dialog.getWindow() !=null) {
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }

        dialog.show();
    }
    public void SignInGoogle(View view) {
        Intent intent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                            } else {
                                Toast.makeText(SignIn.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    });



}