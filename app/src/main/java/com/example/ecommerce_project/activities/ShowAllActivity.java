package com.example.ecommerce_project.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ecommerce_project.R;
import com.example.ecommerce_project.adapter.ShowAllAdapter;
import com.example.ecommerce_project.models.CategoryModel;
import com.example.ecommerce_project.models.ShowAllModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;

    Toolbar toolbar;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        toolbar = findViewById(R.id.show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this,showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

        firestore.collection("ShowAll")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error fetching showall ", error);
                            return;
                        }

                        if (value != null) {
                            showAllModelList.clear(); // Clear old data

                            for (QueryDocumentSnapshot document : value) {
                                ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                            }

                            // Notify adapter
                            showAllAdapter.notifyDataSetChanged();

                        }
                    }
                });

        if (type == null || type.isEmpty()){
            firestore.collection("ShowAll")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("food")){
            firestore.collection("ShowAll").whereEqualTo("type","food")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("shoes")){
            firestore.collection("ShowAll").whereEqualTo("type","shoes")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("mobile")){
            firestore.collection("ShowAll").whereEqualTo("type","mobile")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("sandal")){
            firestore.collection("ShowAll").whereEqualTo("type","sandal")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("laptop")){
            firestore.collection("ShowAll").whereEqualTo("type","laptop")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }
        if (type != null && type.equalsIgnoreCase("dress")){
            firestore.collection("ShowAll").whereEqualTo("type","dress")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore", "Error fetching showall ", error);
                                return;
                            }

                            if (value != null) {
                                showAllModelList.clear(); // Clear old data

                                for (QueryDocumentSnapshot document : value) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                }

                                // Notify adapter
                                showAllAdapter.notifyDataSetChanged();

                            }
                        }
                    });
        }
    }
}