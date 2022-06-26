package com.iman.jsonxml;

import android.content.Context;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlowerXmlJdomParser {

    private Context context;
    private List<Flower> flowerList;

    public FlowerXmlJdomParser (Context context){
        this.context = context;
    }


    public List<Flower> parseXml(){
        flowerList = new ArrayList<>();
        try{

            InputStream input = context.getResources().openRawResource(R.raw.flowers_xml);
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(input);
            Element rootNode = document.getRootElement();
            List<Element> nodes = rootNode.getChildren("product");

            for (Element node : nodes){
                Flower flower = new Flower();
                flower.setPhoto(node.getChildText("photo"));
                flower.setPrice(Double.valueOf(node.getChildText("price")));
                flower.setName(node.getChildText("name"));
                flower.setCategory(node.getChildText("category"));
                flower.setProductId(Long.valueOf(node.getChildText("productId")));
                flower.setInstructions(node.getChildText("instructions"));
                flowerList.add(flower);
            }

            input.close();
        }catch (IOException | JDOMException e){
            e.getStackTrace();
        }


        return flowerList;
    }

}
