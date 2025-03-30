package com.example.ecommerce_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText user,pass;
    private TextView register;
    private CheckBox sp;
    private LinearLayout layout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_btn =findViewById(R.id.b1);
        pass = findViewById(R.id.Password);
        user = findViewById(R.id.userName);
        sp = findViewById(R.id.show_pass);
        register = findViewById(R.id.tvRegister);

        auth = FirebaseAuth.getInstance();

        //auto login
        if(auth.getCurrentUser() != null){
            Intent homeIntent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(homeIntent);
            finish();
        }


        // to show and hide password
        sp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Hide password
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            // to make the cursor go at end
            pass.setSelection(pass.getText().length());
        });

        // to go to signup
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signUp);
            }
        });

        // when the user click login
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();


                if ( email.isEmpty() || password.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }  else if (password.length() < 6 ) {
                    Toast.makeText(MainActivity.this, "Passwords is less than 6 cahracters!", Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "Login successfull!", Toast.LENGTH_SHORT).show();
                                        Intent onboardPage = new Intent(getApplicationContext(), onBoarding.class);
                                        startActivity(onboardPage);
                                    }else {
                                        Toast.makeText(MainActivity.this, "Login failed! "+ task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }

}