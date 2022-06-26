package com.iman.jsonxml;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class XmlJdomParser<T> {

    public abstract InputStream getInput();
    public abstract String getObjectNodesKey();
    public abstract T getObjectFromNode(Element node);


    public List<T> parseXml(){
        List<T> objects = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(getInput());
            Element rootNode = document.getRootElement();
            List<Element> nodes = rootNode.getChildren(getObjectNodesKey());
            for (Element node : nodes){
                T item = getObjectFromNode(node);
                objects.add(item);
            }
            getInput().close();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

}
