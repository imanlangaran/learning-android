package com.iman.jsonxml;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlowerXmlPullParser {

    private Context context;
    private List<Flower> flowerList;
    private Flower currentFlower;
    private String currentTag;

    public FlowerXmlPullParser(Context context) {
        this.context = context;
    }

    public List<Flower> parseXml(){
        flowerList = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            InputStream input = context.getResources().openRawResource(R.raw.flowers_xml);
            parser.setInput(input, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG){

                    handelStartTag(parser.getName());

                }else if (eventType == XmlPullParser.TEXT){

                    handleText(parser.getText());

                }else if (eventType == XmlPullParser.END_TAG){

                    currentTag = null;

                }

                eventType = parser.next();
            }

            input.close();

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return flowerList;
    }

    private void handleText(String text) {

        if (currentTag == null || currentFlower == null) return;

        switch (currentTag){
            case "productId" :
                currentFlower.setProductId(Long.valueOf(text));
                break;
            case "category" :
                currentFlower.setCategory(text);
                break;
            case "name" :
                currentFlower.setName(text);
                break;
            case "instructions" :
                currentFlower.setInstructions(text);
                break;
            case "price" :
                currentFlower.setPrice(Double.valueOf(text));
                break;
            case "photo" :
                currentFlower.setPhoto(text);
                break;
            default: break;
        }


    }

    private void handelStartTag(String tagName) {

        if ("product".equals(tagName)){
            currentFlower = new Flower();
            flowerList.add(currentFlower);
        }else {
            currentTag = tagName;
        }

    }
}
