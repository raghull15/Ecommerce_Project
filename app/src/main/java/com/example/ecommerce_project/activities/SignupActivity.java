package com.example.ecommerce_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText user,email,pass,con_pass ;
    private FirebaseAuth auth;
    Button b1;
    TextView login;
    CheckBox show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        auth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences;

        //auto login
        if(auth.getCurrentUser() != null){
            Intent homeIntent = new Intent(SignupActivity.this, HomepageActivity.class);
            startActivity(homeIntent);
            finish();
        }


        login= findViewById(R.id.login);
        b1 = findViewById(R.id.Signup);
        user = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        con_pass= findViewById(R.id.ConfirmPassword);
        show = findViewById(R.id.show_Password);

        sharedPreferences = getSharedPreferences("introScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        //check the user is first timer and show the intro page
        if(isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent homeIntent = new Intent(SignupActivity.this, IntroActivity.class);
            startActivity(homeIntent);
            finish();

        }


        // to show and hide password
        show.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                con_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Hide password
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                con_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            // to make the cursor go at end
            pass.setSelection(pass.getText().length());
            con_pass.setSelection(pass.getText().length());
        });


        // to login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(log);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });

    }
    private void registerUser() {
        String username = user.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confirmPassword = con_pass.getText().toString().trim();

        if (username.isEmpty() || email1.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6 && confirmPassword.length()<6) {
            Toast.makeText(this, "Passwords is less than 6 cahracters!", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email1,password)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_LONG).show();
                                Intent onboardIntent = new Intent(SignupActivity.this, onBoarding.class);
                                startActivity(onboardIntent);
                            }else{
                                Toast.makeText(SignupActivity.this, "Signup Failed!" + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}