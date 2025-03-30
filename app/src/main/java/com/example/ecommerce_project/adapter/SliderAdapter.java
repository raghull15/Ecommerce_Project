package com.example.ecommerce_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ecommerce_project.R;

public class SliderAdapter extends PagerAdapter {
    Context context;  //it is used to Stores the context (environment) of the app.
    LayoutInflater layoutInflater; // it helps to convert the xml to actual ui component

    public SliderAdapter(Context context){
        this.context = context;
    }

    int imagesArray[] = {
            R.drawable.onboardscreen1,
            R.drawable.onboardscreen2,
            R.drawable.onboardscreen3
    };

    int headingArray[] ={
            R.string.first_slide,
            R.string.second_slide,
            R.string.third_slide
    };

    int descrptionArray[]={
            R.string.desc1,
            R.string.desc2,
            R.string.desc3
    };

    @Override
    public int getCount() {
        return headingArray.length;  // it returns the total number of pages
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliding_layout,container,false);
        // the above code creates the slide dynamically using the siding_layout.xml

        // the below are elements inside the xml
        ImageView imageView = view.findViewById(R.id.slider_img);
        TextView heading = view.findViewById(R.id.heading);
        TextView description = view.findViewById(R.id.description);

        // this tells that we are setting the image , eading and description
        imageView.setImageResource(imagesArray[position]);
        heading.setText(headingArray[position]);
        description.setText(descrptionArray[position]);

        container.addView(view); // adds the slides to view paager

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
