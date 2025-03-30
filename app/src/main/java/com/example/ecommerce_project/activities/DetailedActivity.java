package com.example.ecommerce_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce_project.R;
import com.example.ecommerce_project.models.FoodProductModel;
import com.example.ecommerce_project.models.NewProductsmodels;
import com.example.ecommerce_project.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name , description, price , quantity;
    AppCompatButton addtocart, buyNow;
    ImageView addItems , removeItems;

    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice = 0;

    //new products
    NewProductsmodels newProductsmodels = null;

    // food products
    FoodProductModel foodProductModel = null;

    // show all product
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof NewProductsmodels){
            newProductsmodels = (NewProductsmodels) obj;
        } else if (obj instanceof FoodProductModel) {
            foodProductModel = (FoodProductModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;

        }

        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        quantity= findViewById(R.id.quantity);

        addtocart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.minus_item);

        //New products
        if(newProductsmodels != null){
            Glide.with(getApplicationContext()).load(newProductsmodels.getImg_url()).into(detailedImg);
            name.setText(newProductsmodels.getName());
            rating.setText(newProductsmodels.getRating());
            description.setText(newProductsmodels.getDescription());
            price.setText(String.valueOf(newProductsmodels.getPrice()));

            totalPrice = newProductsmodels.getPrice() * totalQuantity;

        }
        //food products
        if(foodProductModel != null){
            Glide.with(getApplicationContext()).load(foodProductModel.getImg_url()).into(detailedImg);
            name.setText(foodProductModel.getName());
            rating.setText(foodProductModel.getRating());
            description.setText(foodProductModel.getDescription());
            price.setText(String.valueOf(foodProductModel.getPrice()));

            totalPrice = foodProductModel.getPrice() * totalQuantity;

        }
        //show all products
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() * totalQuantity;

        }

        //Buy Now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

               if (newProductsmodels != null){
                   intent.putExtra("item", newProductsmodels);
               }
               if (foodProductModel != null){
                   intent.putExtra("item", foodProductModel);
               }
                if (showAllModel != null){
                    intent.putExtra("item", showAllModel);
                }
               startActivity(intent);
            }
        });

        //add to cart
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsmodels != null){
                        totalPrice = newProductsmodels.getPrice() * totalQuantity;
                    }
                    if (foodProductModel != null){
                        totalPrice = foodProductModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null){
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }

                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });
    }

    private void addtoCart() {

        String savedCurrentTime , saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        savedCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", savedCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this,"Added To Cart", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}