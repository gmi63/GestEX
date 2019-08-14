package com.example.gestex;

import android.graphics.drawable.Drawable;

public class People extends UserId{

    public String image;
    public Drawable imageDrw;
    public String name;
    public String nickname;
    public int pos;

    public int getPosition() {
        return pos;
    }

    public void setPosition(int position) {
        this.pos = position;
    }

    public String email;
    public boolean section = false;


    public People(String name, String nickname, String email) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
    }
    public People(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Drawable getImageDrw() {
        return imageDrw;
    }

    public void setImageDrw(Drawable imageDrw) {
        this.imageDrw = imageDrw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSection() {
        return section;
    }

    public void setSection(boolean section) {
        this.section = section;
    }



}
