package com.example.gestex;

import android.support.annotation.NonNull;

public class position {

    public int p;
    public <T extends position> T withId(@NonNull final int p){

        this.p = p;
        return (T)this;
    }
}
