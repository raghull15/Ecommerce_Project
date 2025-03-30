package com.example.ecommerce_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.number.CompactNotation;
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
import com.example.ecommerce_project.models.ShowAllModel;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    private Context context;
    private List<ShowAllModel> list;

    public ShowAllAdapter(Context context, List<ShowAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.showImg);
        holder.showName.setText(list.get(position).getName());
        holder.showPrice.setText("Rs."+String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {  // Prevents crashes if item is removed
                    Intent intent = new Intent(context, DetailedActivity.class);
                    intent.putExtra("detailed", list.get(pos)); // Works with Serializable
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView showImg;
        TextView showName,showPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showImg = itemView.findViewById(R.id.item_image);
            showName = itemView.findViewById(R.id.item_name);
            showPrice = itemView.findViewById(R.id.item_cost);

        }
    }
}
