package com.example.ecommerce_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce_project.R;
import com.example.ecommerce_project.activities.DetailedActivity;
import com.example.ecommerce_project.models.FoodProductModel;

import java.util.List;

public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.ViewHolder>{

    private Context context;
    private List<FoodProductModel> foodProductModelList;

    public FoodProductAdapter(Context context, List<FoodProductModel> foodProductModelList) {
        this.context = context;
        this.foodProductModelList = foodProductModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(foodProductModelList.get(position).getImg_url()).into(holder.foodImg);
        holder.foodName.setText(foodProductModelList.get(position).getName());
        holder.foodPrice.setText(String.valueOf(foodProductModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {  // Prevents crashes if item is removed
                    Intent intent = new Intent(context, DetailedActivity.class);
                    intent.putExtra("detailed", foodProductModelList.get(pos)); // Works with Serializable
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImg;
        TextView foodName,foodPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.all_img);
            foodName = itemView.findViewById(R.id.all_product_name);
            foodPrice = itemView.findViewById(R.id.all_price);
        }
    }
}
