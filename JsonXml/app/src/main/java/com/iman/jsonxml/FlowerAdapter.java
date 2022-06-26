package com.iman.jsonxml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowerList;

    public FlowerAdapter(@NonNull Context context, List<Flower> flowerList) {
        super(context, R.layout.flower_list_item, flowerList);
        this.context = context;
        this.flowerList = flowerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.flower_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.fill(position);
        return convertView;

    }


    public class ViewHolder {

        ImageView flower_image;
        TextView flower_name, flower_Price, flower_category;

        public ViewHolder(View v) {
            flower_name = v.findViewById(R.id.flower_name);
            flower_Price = v.findViewById(R.id.flower_price);
            flower_category = v.findViewById(R.id.flower_category);
            flower_image = v.findViewById(R.id.flower_image);
        }

        public void fill(int position) {
            Flower f = flowerList.get(position);
            flower_name.setText(f.getName());
            flower_Price.setText(f.getPrice() + " $");
            flower_category.setText(f.getCategory());
            // image
            String photo = f.getPhoto();
            if (photo.contains(".")){
                photo = photo.substring(0, photo.lastIndexOf("."));
            }
            int imageResId = context.getResources().getIdentifier(photo, "drawable", context.getApplicationContext().getPackageName());

            flower_image.setImageResource(imageResId);
        }

    }
}
