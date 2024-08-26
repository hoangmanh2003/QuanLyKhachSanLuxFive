package com.example.myapplication.ObjectManager;

public class manHinhChinhObj {
    private int image;
    private String name;

    public manHinhChinhObj() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public manHinhChinhObj(int image, String name) {
        this.image = image;
        this.name = name;
    }
}
