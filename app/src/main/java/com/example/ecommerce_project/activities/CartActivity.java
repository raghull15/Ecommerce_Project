package com.example.ecommerce_project.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerce_project.R;
import com.example.ecommerce_project.adapter.MycartAdapter;
import com.example.ecommerce_project.models.MyCartModel;
import com.example.ecommerce_project.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    //over all total amount
    int overAllTotalAmount;
    TextView overAllAmount;
    Button buy_btn;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MycartAdapter mycartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // for toolbar
        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get data from my cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(MessageReceiver,new IntentFilter("MyTotalAmount"));

        overAllAmount = findViewById(R.id.cart_tot_price);
        recyclerView = findViewById(R.id.cart_rec);
        buy_btn = findViewById(R.id.buy_now);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        mycartAdapter = new MycartAdapter(this,cartModelList);
        recyclerView.setAdapter(mycartAdapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error fetching addtocart ", error);
                            return;
                        }

                        if (value != null) {
                            cartModelList.clear(); // Clear old data

                            for (QueryDocumentSnapshot document : value) {
                                MyCartModel cartModel = document.toObject(MyCartModel.class);
                                cartModelList.add(cartModel);
                            }

                            // Notify adapter
                           mycartAdapter.notifyDataSetChanged();

                        }
                    }
                });

    }

    public BroadcastReceiver MessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount",0);
            overAllAmount.setText("Total Amount : Rs."+ totalBill);

            buy_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    intent.putExtra("amount", Double.valueOf(totalBill));
                    startActivity(intent);
                }
            });
        }
    };

}