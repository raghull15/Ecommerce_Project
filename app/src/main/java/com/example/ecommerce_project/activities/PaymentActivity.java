package com.example.ecommerce_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;
    Button checkout;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // for toolbar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount",0.0);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shipping);
        total = findViewById(R.id.total_amt);

        double tot = amount + 50 ;

        subTotal.setText("Rs. "+String.valueOf(amount));
        discount.setText("5 % ");
        shipping.setText("Rs.40");
        total.setText("Rs." + String.valueOf(tot));

        checkout = findViewById(R.id.pay_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    String userEmail = user.getEmail(); // Get the logged-in user's email
                    String subject = "Order Confirmation - Smash Cart App";
                    String message = "Thank you for your purchase! Your order has been placed successfully. \n keep the Transaction Slip for Verification.";

                    // Send the email in a separate thread to avoid UI blocking
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Object EmailSender = null;
                            try {
                                EmailSender.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    Toast.makeText(PaymentActivity.this, "Order Placed! Check your Email.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PaymentActivity.this, HomepageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PaymentActivity.this, "User not logged in!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}