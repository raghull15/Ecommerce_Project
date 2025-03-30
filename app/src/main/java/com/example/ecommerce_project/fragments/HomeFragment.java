package com.example.ecommerce_project.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_project.R;
import com.example.ecommerce_project.activities.ShowAllActivity;
import com.example.ecommerce_project.activities.SignupActivity;
import com.example.ecommerce_project.adapter.CategoryAdapter;
import com.example.ecommerce_project.adapter.FoodProductAdapter;
import com.example.ecommerce_project.adapter.HomeSliderAdapter;
import com.example.ecommerce_project.adapter.NewProductsAdapter;
import com.example.ecommerce_project.models.CategoryModel;
import com.example.ecommerce_project.models.FoodProductModel;
import com.example.ecommerce_project.models.NewProductsmodels;
import com.example.ecommerce_project.models.SliderItem;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderView;


import com.google.firebase.firestore.EventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView catShowAll, newProdShowAll, foodProdShowAll;

    ProgressDialog progressDialog;
    private SliderView sliderView;
    private HomeSliderAdapter sliderAdapter;
    private List<SliderItem> sliderItems;

    RecyclerView catRecyclerview , newProductRecyclerview , foodRecyclerview;
    //category recyclerview
    CategoryAdapter categoryAdapter ;
    List<CategoryModel> categoryModelList;

    //new product recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsmodels> newProductsmodelsList;

    // food recyclerview
    FoodProductAdapter foodProductAdapter;
    List<FoodProductModel> foodProductModelList;

    //Firestore
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());

        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        foodRecyclerview = root.findViewById(R.id.popular_rec);

        //for see all
        catShowAll = root.findViewById(R.id.category_see_all);
        newProdShowAll = root.findViewById(R.id.newProducts_see_all);
        foodProdShowAll = root.findViewById(R.id.popular_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newProdShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        foodProdShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });



        progressDialog.setTitle("Welcome To Smash Cart");
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        //category
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error fetching categories", error);
                            return;
                        }

                        if (value != null) {
                            categoryModelList.clear(); // Clear old data

                            for (QueryDocumentSnapshot document : value) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                            }

                            // Notify adapter
                            categoryAdapter.notifyDataSetChanged();

                            // Hide progress  after fetching data
                            progressDialog.dismiss();
                        }
                    }
                });


        // new products
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsmodelsList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsmodelsList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error fetching newProducts", error);
                            return;
                        }

                        if (value != null) {
                            newProductsmodelsList.clear(); // Clear list before adding new data
                            for (QueryDocumentSnapshot document : value) {
                                NewProductsmodels newProductsmodels = document.toObject(NewProductsmodels.class);
                                newProductsmodelsList.add(newProductsmodels);
                            }
                            newProductsAdapter.notifyDataSetChanged(); // Notify adapter to refresh UI
                        }
                    }
                });

        // food products
        foodRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        foodProductModelList = new ArrayList<>();
        foodProductAdapter = new FoodProductAdapter(getContext(),foodProductModelList);
        foodRecyclerview.setAdapter(foodProductAdapter);

        db.collection("foodProduct")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error fetching food product", error);
                            return;
                        }

                        if (value != null) {
                            foodProductModelList.clear(); // Clear list before adding new data
                            for (QueryDocumentSnapshot document : value) {
                                FoodProductModel foodProductModel = document.toObject(FoodProductModel.class);
                                foodProductModelList.add(foodProductModel);
                            }
                            foodProductAdapter.notifyDataSetChanged(); // Notify adapter to refresh UI
                        }
                    }
                });



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("HomeFragment", "onViewCreated called");

        sliderView = view.findViewById(R.id.slider);
        sliderItems = new ArrayList<>();

        // Adding images to the slider
        sliderItems.add(new SliderItem("https://media-hosting.imagekit.io//4e4769e65c894c12/61fd4c799caef1b4.webp?Expires=1834846924&Key-Pair-Id=K2ZIVPTIP2VGHC&Signature=AEO-V11~k-9mXOYW8DhG~bteumxJ3tAThQYY2-nowfudS1tfgkSOXFJL5eXGTIqRL6OyCe0iuMEE2HDxQMO6WokKy1~080kU-L3mq0JE3--0ddyzyhcet96WKnRMisAtdmoQN3HUbw9pZRZbNdkOMUg6BYKB9-2hkpzldXukencRq6Xrae8JBM6CPvSTGLnHaKbc7FIbgtamzZnaZmDUcLj6KAtALekbwz7lo2BMH0piYQEGwargJXEFjYB8gH0RsXfolh8IH2DOAELd2wgn74JAXjDudJPMEqxhW40u9kVtD4r12DqotrH00rVR8mC5BH5UIcAvyFIWrgYVmjxFrA__"));
        sliderItems.add(new SliderItem("https://media-hosting.imagekit.io//87fe48783fb54069/5dbe24535d092e63.webp?Expires=1834846889&Key-Pair-Id=K2ZIVPTIP2VGHC&Signature=XappOMBKoyb85Ccf8HmuJJqJXld8QoszkH~q1WZ2IqrTn5kI5NwgovgXYsQFRyDAgrktnOBcLGytDMXEfKwUd730d0rdRaLas9-SSc0apkbFwElHrq5aOnyP-kkYDAm3Z1D4XtrN368xz14jnGSMiWMO0OwaSGTzJXEdu9p7XK-~MqeN7voVgQLgD38rGEU5kHdt2378JW4PGn0RwYLWxfoqL-dedST3osgtbjhlJzoY4LPk4OQvV2aWi3szvZldZmR67flmJWkhpb1PxGjw2qUA3g03UFcCuBWEHylq8J871tM3eL~v1Xt3ZtoJwf7GHjiFuAulkaafEbH6CN9dhA__"));
        sliderItems.add(new SliderItem("https://media-hosting.imagekit.io//b9d1fd3c03be4fa1/mobile-phone-1419275_1280.jpg?Expires=1834847052&Key-Pair-Id=K2ZIVPTIP2VGHC&Signature=MCafuYrM~7quROrIaCxcbh6M-zsJyxXibxHvCStsAtcOpXZ0w1hQpZXk8Va99vKEXtCU1aiDxOWgtVeaUuzft8MW11ztrx88fJCG5JlmmWUILfTn8a4GYP51fuWGSSb09XIrXk7IDbbZ75g-uLexGMec0PsQ2pAFhVGwc-MOjzAkqyT5yEDGcl5iWDHj8zrtjVi1qpRD3uz1TZDt-6UEafcchGf8e2zSxVoc9wjESajCyZRj9tlhzgE2QcehfcScWDK8Csd3li4BRa9TESVRfOo3mvH0zEDpV31iEoE7Sr3CXj0qnIE3KJP8a835QRFOU2sCD0fIpUAreMAt347WFQ__"));

        sliderAdapter = new HomeSliderAdapter(requireContext(), sliderItems);
        sliderView.setSliderAdapter(sliderAdapter);

        // Optional settings
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        Log.d("HomeFragment", "Slider initialized with " + sliderItems.size() + " items");
    }
}
