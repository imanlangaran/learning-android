package com.iman.sqlitedb;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Activity activity;
    private List<Flower> flowerList;
    private FlowerDbHelper dbHelper;

    public FlowerAdapter(@NonNull Activity context, List<Flower> flowerList) {
        super(context, R.layout.flower_list_item, flowerList);
        dbHelper = new FlowerDbHelper(context);
        this.activity = context;
        this.flowerList = flowerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(activity).inflate(R.layout.flower_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.fill(position);
        return convertView;

    }


    public class ViewHolder {

        ImageView flower_image, icon_more;
        TextView flower_name, flower_Price, flower_category;

        public ViewHolder(View v) {
            flower_name = v.findViewById(R.id.flower_name);
            flower_Price = v.findViewById(R.id.flower_price);
            flower_category = v.findViewById(R.id.flower_category);
            flower_image = v.findViewById(R.id.flower_image);
            icon_more = v.findViewById(R.id.more);
        }

        public void fill(final int position) {
            final Flower f = flowerList.get(position);
            flower_name.setText(f.getName());
            flower_Price.setText(f.getPrice() + " $");
            flower_category.setText(f.getCategory());
            // image
            String photo = f.getPhoto();
            if (photo.contains(".")){
                photo = photo.substring(0, photo.lastIndexOf("."));
            }
            int imageResId = activity.getResources().getIdentifier(photo, "drawable", activity.getApplicationContext().getPackageName());
            flower_image.setImageResource(imageResId);

            final PopupMenu popup = new PopupMenu(icon_more.getContext(), icon_more);
            popup.inflate(R.menu.flower_popup_menu);

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.popup_option_edit){
                        showUpdateDialog(f);
                    }else if (id == R.id.popup_option_delete){
                        dbHelper.deleteFlower(f.getProductId());
                        flowerList.remove(position);
                        notifyDataSetChanged();
                    }
                    return false;
                }
            });

            icon_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.show();
//                    Toast.makeText(activity, "productId : " + f.getProductId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void showUpdateDialog(final Flower flower) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update);
        changeDialogSize(dialog);

        Button submit = dialog.findViewById(R.id.btn_submit);
        final EditText inputName = dialog.findViewById(R.id.input_name);
        final EditText inputCategory = dialog.findViewById(R.id.input_cat);
        final EditText inputPrice = dialog.findViewById(R.id.input_price);
        final EditText inputInstr = dialog.findViewById(R.id.input_intro);

        inputName.setText(flower.getName());
        inputCategory.setText(flower.getCategory());
        inputInstr.setText(flower.getInstructions());
        inputPrice.setText(String.valueOf(flower.getPrice()));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pr = inputPrice.getText().toString().trim();
                double price = pr.isEmpty() ? 0.0 : Double.valueOf(pr);
                flower.setName(inputName.getText().toString().trim());
                flower.setCategory(inputCategory.getText().toString().trim());
                flower.setInstructions(inputInstr.getText().toString().trim());
                flower.setPrice(price);
                ContentValues cv = flower.getContentValuesForDb();
                cv.remove(Flower.KEY_ID);
                dbHelper.update(flower.getProductId(), cv);
                notifyDataSetChanged();
                Toast.makeText(activity, "Updated.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private Point getScreenSize(Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }

    private void changeDialogSize(Dialog dialog){
        Point scrSize = getScreenSize(activity);
        if (dialog.getWindow() != null){
            dialog.getWindow().setLayout((int)(0.9 * scrSize.x), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
