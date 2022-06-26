package com.iman.jsonxml;

import org.jdom2.Element;

import java.io.InputStream;

public class TourJdomParser extends XmlJdomParser<Tour>{

    private InputStream input;

    public TourJdomParser (InputStream input) {
        this.input = input;
    }

    @Override
    public InputStream getInput() {
        return input;
    }

    @Override
    public String getObjectNodesKey() {
        return "tour";
    }

    @Override
    public Tour getObjectFromNode(Element node) {

        Tour t = new Tour();

        t.setTourId(Integer.valueOf(node.getChildText("tourId")));
        t.setTourTitle(node.getChildText("tourTitle"));
        t.setPackageTitle(node.getChildText("packageTitle"));
        t.setDescription(node.getChildText("description"));
        t.setPrice(Double.valueOf(node.getChildText("price")));
        t.setDifficulty(node.getChildText("difficulty"));
        t.setLength(Integer.valueOf(node.getChildText("length")));
        t.setImage(node.getChildText("image"));
        t.setLink(node.getChildText("link"));

        return t;

    }
}
