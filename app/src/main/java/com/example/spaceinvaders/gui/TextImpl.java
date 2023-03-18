package com.example.spaceinvaders.gui;

public class TextImpl implements Text{
    private String text;

    public TextImpl(String text){
        this.text = text;
    }

    @Override
    public void update(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
