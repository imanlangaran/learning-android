package com.iman.androidweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iman.androidweather.data.CityDBHelper;
import com.iman.androidweather.data.CityModel;

import java.util.List;

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {

    private List<CityModel> cityList;
    private CityDBHelper dbHelper;

    public CityRecyclerViewAdapter(List<CityModel> cityList, CityDBHelper dbHelper) {
        this.cityList = cityList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CityModel cityModel = cityList.get(position);
        holder.tv_city_name.setText(cityModel.toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateCitySelected(cityModel.getId(), false);
                cityList.remove(cityModel);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_city_name;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_city_name = itemView.findViewById(R.id.city_name);
            btn = itemView.findViewById(R.id.btn);
            btn.setText("Delete");
        }
    }

}
