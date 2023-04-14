package com.example.spaceinvaders.gui;

import com.example.spaceinvaders.logic.interfaces.Observer;

public class TextImpl implements Text, Observer {
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
