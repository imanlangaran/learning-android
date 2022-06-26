package com.iman.bluetoothchat;


public class Msg {

    private String text;
    private boolean isMine;

    public Msg(String text, boolean isMine){
        this.text = text;
        this.isMine = isMine;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    @Override
    public String toString() {
        return text;
    }
}
