package com.iman.androidweather;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iman.androidweather.data.CityDBHelper;
import com.iman.androidweather.data.CityModel;

import java.util.ArrayList;
import java.util.List;

public class AddCityFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private AddCityInterface iactivity;
    private MyAdapter adapter;
    CityDBHelper dbHelper;
    List<CityModel> cityList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper = new CityDBHelper(getContext());

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i(AddCityFragment.class.getSimpleName(), "onQueryTextSubmit : " + query);
                cityList = dbHelper.searchCityByName(newText, "20");
//                Log.i(AddCityFragment.class.getSimpleName(), "cityList : " + cityList.size() + " items.");
                updateDisplay();
                return false;

            }
        });

        updateDisplay();
        return view;
    }

    private void updateDisplay() {
        if (cityList == null){
            cityList = new ArrayList<>();
        }
        adapter = new MyAdapter(cityList);
        recyclerView.setAdapter(adapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<CityModel> cityList;

        public MyAdapter(List<CityModel> cityList) {
            this.cityList = cityList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final CityModel cityModel = cityList.get(position);
            holder.tv_city_name.setText(cityModel.toString());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iactivity.addCity(cityModel.getId());
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cityList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_city_name;
        Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_city_name = itemView.findViewById(R.id.city_name);
            btn = itemView.findViewById(R.id.btn);
            btn.setText("Add");
            btn.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.add_color));
            btn.setBackgroundResource(R.drawable.city_add_btn_bg);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.iactivity = (AddCityInterface) context;
    }

    static interface AddCityInterface {
        void addCity(long cityId);
    }

    @Override
    public void onResume() {
        super.onResume();
        changeDialogSize();
    }

    private void changeDialogSize(){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setLayout(
                (int) (metrics.widthPixels * 0.9),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

}
