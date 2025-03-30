package com.example.ecommerce_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce_project.R;
import com.example.ecommerce_project.models.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;
public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.SliderViewHolder> {

    private final Context context;
    private final List<SliderItem> sliderItems;

    public HomeSliderAdapter(Context context, List<SliderItem> sliderItems) {
        this.context = context;
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem item = sliderItems.get(position);
        Glide.with(context).load(item.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    static class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
    }
}