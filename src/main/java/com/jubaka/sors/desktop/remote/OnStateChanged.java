package com.jubaka.sors.desktop.remote;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by root on 30.08.16.
 */
public class OnStateChanged implements Observer {
    private OnStateChanged instance;

    public OnStateChanged getInstance() {
        if (instance== null) instance = new OnStateChanged();
        return instance;
    }

    private OnStateChanged() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
