package com.iman.jsonxml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlowerJsonParser {

    public static List<Flower> parseJson(InputStream inputStream){
        String jsonString = Utils.convertInputStreamToString(inputStream);
        return parseJson(jsonString);
    }


    public static List<Flower> parseJson(String jsonString){
        List<Flower> flowerList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Flower f = new Flower();
                f.setName(jsonObject.getString("name"));
                f.setCategory(jsonObject.getString("category"));
                f.setInstructions(jsonObject.getString("instructions"));
                f.setPhoto(jsonObject.getString("photo"));
                f.setProductId(jsonObject.getLong("productId"));
                f.setPrice(jsonObject.getDouble("price"));
                flowerList.add(f);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flowerList;
    }

}
